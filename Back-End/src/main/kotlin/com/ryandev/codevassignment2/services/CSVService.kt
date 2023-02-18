package com.ryandev.codevassignment2.services

import com.opencsv.CSVReaderBuilder
import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.repository.InvoiceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class CsvService(private val invoiceRepository: InvoiceRepository) {

    suspend fun parseCsv(file: MultipartFile): Flow<Int> = flow {
        val reader = CSVReaderBuilder(InputStreamReader(file.inputStream)).withSkipLines(1).build()
        var progress = 0
        var count = 0
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
            invoiceRepository.save(invoice)
            count++

            if (count >= 10000) {
                progress += 10
                count = 0
                emit(progress)
            }
        }
        emit(100)
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



