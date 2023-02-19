package com.ryandev.codevassignment2

import com.ryandev.codevassignment2.services.CsvService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile

@SpringBootTest
class Codevassignment2ApplicationTests (
	@Autowired private val csvService: CsvService
) {

	@Test
	suspend fun `test successful CSV upload`() {
		val fileContent = """
            InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country
            536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,12/1/2010 8:26,2.55,17850,United Kingdom
            536366,71053,WHITE METAL LANTERN,6,12/1/2010 8:28,3.39,17850,United Kingdom
        """.trimIndent()

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(2, invoices.size)
		assertEquals("536365", invoices[0].invoiceNo)
		assertEquals("85123A", invoices[0].stockCode)
		assertEquals("WHITE HANGING HEART T-LIGHT HOLDER", invoices[0].description)
		assertEquals(6, invoices[0].quantity)
		assertEquals(2.55, invoices[0].unitPrice)
		assertEquals("17850", invoices[0].customerID)
		assertEquals("United Kingdom", invoices[0].country)
		assertEquals("536366", invoices[1].invoiceNo)
		assertEquals("71053", invoices[1].stockCode)
		assertEquals("WHITE METAL LANTERN", invoices[1].description)
		assertEquals(6, invoices[1].quantity)
		assertEquals(3.39, invoices[1].unitPrice)
		assertEquals("17850", invoices[1].customerID)
		assertEquals("United Kingdom", invoices[1].country)
	}

	@Test
	suspend fun `test empty CSV upload`() {
		val fileContent = "InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country\n"

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(0, invoices.size)
	}

	@Test
	suspend fun `test CSV with invalid date format`() {
		val fileContent = """
            InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country
            536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,1/12/2010 8:26,2.55,17850,United Kingdom
        """.trimIndent()

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(0, invoices.size)
	}

	@Test
	suspend fun `test CSV with missing required fields`() {
		val fileContent = """
        InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice
        536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,12/1/2010 8:26,2.55
    """.trimIndent()

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(0, invoices.size)
	}

	@Test
	suspend fun `test CSV with invalid data type`() {
		val fileContent = """
        InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country
        536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,12/1/2010 8:26,invalid,17850,United Kingdom
    """.trimIndent()

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(0, invoices.size)
	}

	@Test
	suspend fun `test CSV with invalid column names`() {
		val fileContent = """
        InvoiceNo,StockCode,Description,Quantity,InvalidColumnName,UnitPrice,CustomerID,Country
        536365,85123A,WHITE HANGING HEART T-LIGHT HOLDER,6,12/1/2010 8:26,2.55,17850,United Kingdom
    """.trimIndent()

		val file = MockMultipartFile("file", "test.csv", "text/csv", fileContent.toByteArray())
		csvService.parseCsv(file)

		val invoices = csvService.getCsvData(0, 10)
		assertEquals(0, invoices.size)
	}
}