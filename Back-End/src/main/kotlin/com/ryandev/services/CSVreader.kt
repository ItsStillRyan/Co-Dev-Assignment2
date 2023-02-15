package com.ryandev.services

import com.ryandev.model.Invoices
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun csvToEmployees(inS: InputStream): List<Invoices> {
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

                val employees = ArrayList<Invoices>()
                val csvRecords = csvParser.records

                for (csvRecord in csvRecords) {
                    val employee = Invoices(
//                            csvRecord["Id"],
//                            csvRecord["Login"],
//                            csvRecord["Name"],
//                            csvRecord["Salary"].toDouble()
                    )
                    employees.add(employee)
                }
                employees
            }
        }
    } catch (e: IOException) {
        throw RuntimeException("Failed" + e.message)
    }
}