package com.ryandev.codevassignment2.services

import com.ryandev.codevassignment2.model.Invoices
import com.ryandev.codevassignment2.repository.InvoiceRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceServices(val invoiceRepository: InvoiceRepository){
    fun getAll(): List<Invoices> = invoiceRepository.findAll()

    fun getById(id: Long): Invoices = invoiceRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
}