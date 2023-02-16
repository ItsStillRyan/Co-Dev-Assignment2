package com.ryandev.codevassignment2.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "invoices")
data class Invoices(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: Long,
        val invoiceNo: Int,
        val stockCode: String,
        val description: String,
        val quantity: Int,
        val invoiceDate: String,
        val unitPrice: Double,
        val customerID: Int,
        val country: String
)
