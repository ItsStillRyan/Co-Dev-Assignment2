package com.ryandev.codevassignment2.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
@Entity
@Table(name = "invoices")
data class Invoices(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        val invoice_no: Int,
        val stock_code: String,
        val description: String,
        val quantity: Int,
        val invoice_date: String,
        val unit_price: Double,
        val customer_id: Int,
        val country: String
)
