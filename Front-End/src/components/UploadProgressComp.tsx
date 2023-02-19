import { Button, CircularProgress } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Form } from "react-bootstrap";

type Props = {
  dataLength: number;
};

export default function UploadProgressComp({ dataLength }: Props) {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [progress, setProgress] = useState<number>(-1);
  const [loading, setLoading] = useState(false);
  const [file, setFile] = useState<File | null>(null);

  const handleFileInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files && event.target.files[0];
    setFile(file);
    setSelectedFile(file);
  };

  useEffect(() => {
    let timer: NodeJS.Timeout;
    if (dataLength) {
      timer = setTimeout(() => {
        setLoading(false);
      }, 3000);
    }
    return () => clearTimeout(timer);
  }, [dataLength]);

  const uploadFile = () => {
    setLoading(true);
    if (selectedFile) {
      const formData = new FormData();
      formData.append("file", selectedFile);
      axios.post("http://localhost:8080/csv/upload", formData, {
        onUploadProgress: (progressEvent) => {
          const total = progressEvent.total ?? 1;
          const percentage = Math.round((progressEvent.loaded * 100) / total);
          setProgress(percentage);
        },
      });
    }
  };

  return (
    <div className="upload-progress-inner-contain">
      <div className="upload-progress-uploadbar">
        <Form.Group controlId="formFile" className="mb-3">
          <Form.Label>Upload CSV File</Form.Label>
          <Form.Control type="file" onChange={handleFileInput} />
        </Form.Group>
        <Button
          variant="contained"
          onClick={uploadFile}
          disabled={loading || !file}
        >
          {loading ? <CircularProgress size={24} /> : "Upload"}
        </Button>
      </div>
      <div className="upload-progress-progressbar">
        {progress >= 0 && (
          <h3>
            {progress < 100
              ? `CSV uploading: ${progress}%`
              : `Rows uploaded: ${dataLength}`}
          </h3>
        )}
      </div>
    </div>
  );
}
