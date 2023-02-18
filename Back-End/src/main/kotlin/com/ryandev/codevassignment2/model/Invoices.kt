package com.ryandev.codevassignment2.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
@Entity
@Table(name = "invoices")
data class Invoices(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,
        val invoiceNo: String,
        val stockCode: String,
        val description: String,
        val quantity: Int,
        @Column(columnDefinition = "TIMESTAMP")
        val invoiceDate: LocalDateTime,
        val unitPrice: Double,
        val customerID: String,
        val country: String
)
