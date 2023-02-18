package com.ryandev.codevassignment2.services

import com.opencsv.CSVReaderBuilder
import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.repository.InvoiceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlinx.coroutines.*
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class CsvService(private val invoiceRepository: InvoiceRepository) {

    suspend fun parseCsv(file: MultipartFile) {
        val reader = CSVReaderBuilder(InputStreamReader(file.inputStream)).withSkipLines(1).build()

        val invoices = mutableListOf<Deferred<Invoices>>()

        while (true) {
            val nextLine = reader.readNext() ?: break

            val formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:m")
            val invoice = Invoices(
                invoiceNo = nextLine[0],
                stockCode = nextLine[1],
                description = nextLine[2],
                quantity = nextLine[3].toInt(),
                invoiceDate = LocalDateTime.parse(nextLine[4], formatter),
                unitPrice = nextLine[5].toDouble(),
                customerID = nextLine[6],
                country = nextLine[7]
            )

            val deferredInvoice = GlobalScope.async {
                invoiceRepository.save(invoice)
            }

            invoices.add(deferredInvoice)

            if (invoices.size >= 10000) {
                invoices.awaitAll()
                invoices.clear()
            }
        }

        invoices.awaitAll()
    }

    fun getCsvData(page: Int, pageSize: Int): List<Invoices> {
        val pageable = PageRequest.of(page, pageSize)
        val csvDataPage = invoiceRepository.findAll(pageable)
        return csvDataPage.content
    }

    fun getAllCsvData(): List<Invoices> {
        return invoiceRepository.findAll()
    }
}



