import React, { useState } from 'react';
import { Document, Page } from 'react-pdf';

function PDFViewer() {
  const [numPages, setNumPages] = useState(null);
  const [pageNumber, setPageNumber] = useState(1);
  const [showPDF, setShowPDF] = useState(false); // Controla la visibilidad del PDF

  function onDocumentLoadSuccess({ numPages }) {
    setNumPages(numPages);
  }

  return (
    <div>
      <button onClick={() => setShowPDF(!showPDF)}>Rules</button>
      {showPDF && (
        <div>
          <Document
            file="frontend/public/ReglasES.pdf"
            onLoadSuccess={onDocumentLoadSuccess}
          >
            <Page pageNumber={pageNumber} />
          </Document>
          <p>
            Página {pageNumber} de {numPages}
          </p>
        </div>
      )}
    </div>
  );
}

export default PDFViewer;