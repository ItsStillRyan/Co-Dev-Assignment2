import axios from 'axios';
import React, { useState } from 'react'

type Props = {}

export default function UploadProgressComp({}: Props) {
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [progress, setProgress] = useState<number>(0);
  
    const handleFileInput = (event: React.ChangeEvent<HTMLInputElement>) => {
      const file = event.target.files && event.target.files[0];
      setSelectedFile(file);
    };
  
    const uploadFile = () => {
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
        <div>
          <h3>Upload CSV File</h3>
          <input type="file" onChange={handleFileInput} />
          <button onClick={uploadFile}>Upload</button>
          {progress > 0 && <div>Progress: {progress}%</div>}
        </div>
      );
    };