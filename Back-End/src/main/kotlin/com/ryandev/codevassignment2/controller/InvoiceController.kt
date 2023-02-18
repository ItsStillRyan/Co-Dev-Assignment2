package com.ryandev.codevassignment2.controller
import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.repository.InvoiceRepository
import com.ryandev.codevassignment2.services.CsvService
import kotlinx.coroutines.coroutineScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RequestMapping("/csv")
@RestController
class InvoiceController(
    val csvService: CsvService,
){

    @GetMapping
    fun getCsvData(
        @RequestParam("page") page:Int,
        @RequestParam("pageSize") pageSize: Int
    ): List<Invoices> {
        return csvService.getCsvData(page,pageSize)
    }

    @GetMapping("/all")
    fun getCsvALL(): List<Invoices> {
        return csvService.getAllCsvData()
    }

    @PostMapping("/upload")
    suspend fun uploadCSV(@RequestParam("file") file: MultipartFile) {
        coroutineScope {
            csvService.parseCsv(file)
        }
    }
}