package com.ryandev.codevassignment2.repository

import com.ryandev.codevassignment2.model.Invoices
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoices, Long>