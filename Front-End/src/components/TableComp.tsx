import {
    Paper,
    TableContainer,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    TablePagination,
    Table,
  } from "@mui/material";
  import axios from "axios";
  import React, { useEffect, useState } from "react";
  
  type Props = {};
  
  interface Column {
    id:
      | "id"
      | "invoiceNo"
      | "stockCode"
      | "description"
      | "quantity"
      | "invoiceDate"
      | "unitPrice"
      | "customerID"
      | "country";
    label: string;
    minWidth?: number;
    align?: "right";
    format?: (value: number) => string;
  }

export default function TableComp({}: Props) {
    const [data, setData] = useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios(
        "http://localhost:8080/csv/all"
      );
      setData(result.data);
    };

    fetchData();

    const intervalId = setInterval(fetchData, 2000); // Update every 5 seconds

    return () => clearInterval(intervalId);
  }, []);


  const columns: readonly Column[] = [
    { id: "id", label: "Id", minWidth: 20 },
    { id: "invoiceNo", label: "Invoice No", minWidth: 100 },
    { id: "stockCode", label: "Stock Code", minWidth: 100 },
    { id: "description", label: "Description", minWidth: 100 },
    { id: "quantity", label: "Quantity", minWidth: 20 },
    { id: "invoiceDate", label: "Invoice Date", minWidth: 100 },
    { id: "unitPrice", label: "Unit Price", minWidth: 100 },
    { id: "customerID", label: "Customer ID", minWidth: 100 },
    { id: "country", label: "Country", minWidth: 100 },
  ];

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };


  return (
    <div className="table-contain">
      <Paper sx={{ width: "100%", overflow: "hidden" }}>
        <TableContainer sx={{ maxHeight: 800 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                {columns.map((column) => (
                  <TableCell
                    key={column.id}
                    align={column.align}
                    style={{ minWidth: column.minWidth }}
                  >
                    {column.label}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {data
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map(
                  (row: {
                    [x: string]: any;
                    code: React.Key | null | undefined;
                  }) => {
                    return (
                      <TableRow
                        hover
                        role="checkbox"
                        tabIndex={-1}
                        key={row.code}
                      >
                        {columns.map((column) => {
                          const value = row[column.id];
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.format && typeof value === "number"
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  }
                )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100, 500]}
          component="div"
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  )
}