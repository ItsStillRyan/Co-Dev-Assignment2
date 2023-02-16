package com.ryandev.codevassignment2.controller
import com.ryandev.codevassignment2.services.CSVService
import com.ryandev.codevassignment2.services.InvoiceServices
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
    val invoiceServices: InvoiceServices,
    val csvService: CSVService
){

    @GetMapping("/")
    fun getAllInvoice() = invoiceServices.getAll()

    @PostMapping("/upload")
    fun uploadCsv(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        csvService.uploadCsv(file)
        return ResponseEntity.ok("File uploaded successfully")
    }
}