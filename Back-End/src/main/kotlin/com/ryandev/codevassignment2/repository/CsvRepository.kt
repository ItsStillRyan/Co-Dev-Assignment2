package com.ryandev.codevassignment2.repository

import com.ryandev.codevassignment2.model.Invoices
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class CsvRepository (
    private val dataSource: DataSource
) {
    fun insertCsvData(invoiceList: List<Invoices>) {
        val conn = dataSource.connection
        val statement = conn.prepareStatement("INSERT INTO invoices (invoiceNo, stockCode, description, quantity, invoiceDate, unitPrice, customerID, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        invoiceList.forEach { data ->
            statement.setInt(1, data.invoice_no)
            statement.setString(2, data.stock_code)
            statement.setString(3, data.description)
            statement.setInt(4, data.quantity)
            statement.setString(5, data.invoice_date)
            statement.setDouble(6, data.unit_price)
            statement.setInt(7, data.customer_id)
            statement.setString(8, data.country)
            statement.executeUpdate()
        }
        statement.close()
        conn.close()
    }


}