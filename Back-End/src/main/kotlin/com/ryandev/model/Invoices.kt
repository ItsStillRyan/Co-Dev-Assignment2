package com.ryandev.model

import java.util.*

data class Invoices(
        val invoiceNo: Int,
        val stockCode: String,
        val description: String,
        val quantity: Int,
        val invoiceDate: Date,
        val unitPrice: Double,
        val customerID: Int,
        val country: String
)
