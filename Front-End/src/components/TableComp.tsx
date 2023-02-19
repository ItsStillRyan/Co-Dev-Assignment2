import {
  Paper,
  TableContainer,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  TablePagination,
  Table,
  TextField,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import UploadProgressComp from "./UploadProgressComp";

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
  const [searchText, setSearchText] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios("http://localhost:8080/csv/all");
      setData(result.data);
    };
    fetchData();
    const intervalId = setInterval(fetchData, 500);
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

  const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(event.target.value);
  };

  const filterData = (data: any) => {
    const filteredData = data.filter((row: any) => {
      let match = false;
      columns.forEach((column) => {
        const value = row[column.id];
        if (value.toString().toLowerCase().includes(searchText.toLowerCase())) {
          match = true;
        }
      });
      return match;
    });
    return filteredData;
  };

  return (
    <div>
      <Row>
        <Col md={3}>
          <div className="upload-progress-contain">
            <UploadProgressComp dataLength={data.length} />
          </div>
        </Col>
        <Col>
          <div className="table-contain">
            <div>
              <TextField
                label="Search"
                variant="outlined"
                value={searchText}
                onChange={handleSearch}
                sx={{ width: "100%", mb: 2 }}
              />
            </div>
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
                    {filterData(data)
                      .slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
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
                                  <TableCell
                                    key={column.id}
                                    align={column.align}
                                  >
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
                count={filterData(data).length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Paper>
          </div>
        </Col>
      </Row>
    </div>
  );
}
