package com.ryandev.codevassignment2.controller
import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.services.CsvService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    suspend fun uploadCSV(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        csvService.parseCsv(file).collect { progress ->
            val headers = HttpHeaders()
            headers.set("X-Progress", progress.toString())
            ResponseEntity("", headers, HttpStatus.OK)
        }
        return ResponseEntity.ok("File uploaded successfully.")
    }
}