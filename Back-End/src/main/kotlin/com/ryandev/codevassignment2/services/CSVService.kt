package com.ryandev.codevassignment2.services

import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.repository.CsvRepository
import com.ryandev.codevassignment2.repository.InvoiceRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Service
class CSVService(
    private val csvRepository: CsvRepository
) {
    fun uploadCsv(file: MultipartFile) {
        val csvDataList = csvToInvoices(file.inputStream)
        csvRepository.insertCsvData(csvDataList)
    }

    fun csvToInvoices(inS: InputStream): List<Invoices> {
        return try {
            BufferedReader(InputStreamReader(inS, StandardCharsets.UTF_8)).use { fileReader ->
                CSVParser(fileReader, CSVFormat.RFC4180.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setAllowMissingColumnNames(false)
                    .setCommentMarker('#')
                    .setIgnoreEmptyLines(true)
                    .setIgnoreSurroundingSpaces(true)
                    .setTrim(true)
                    .build()).use { csvParser ->

                    val ivc = ArrayList<Invoices>()
                    val csvRecords = csvParser.records

                    for (csvRecord in csvRecords) {
                        val invoice = Invoices(
                            csvRecord["id"].toLong(),
                            csvRecord["invoiceNo"].toInt(),
                            csvRecord["stockCode"],
                            csvRecord["description"],
                            csvRecord["quantity"].toInt(),
                            csvRecord["invoiceDate"],
                            csvRecord["unitPrice"].toDouble(),
                            csvRecord["customerID"].toInt(),
                            csvRecord["country"]
                        )
                        ivc.add(invoice)
                    }
                    ivc
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed" + e.message)
        }
    }
}
