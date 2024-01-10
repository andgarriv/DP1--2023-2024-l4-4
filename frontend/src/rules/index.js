import React, { useEffect, useState } from 'react';
import 'whatwg-fetch';
import '../static/css/home/home.css';


const RulesViewer = () => {
  const [html, setHtml] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('rules.html');
        if (response.ok) {
          const data = await response.text();
          setHtml(data);
        } else {
          setHtml(<h3>Error al cargar el archivo HTML</h3>);
        }
      } catch (error) {
        setHtml(<h3>{error}</h3>);
      }
    };

    fetchData();
  }, []);

  if (!html) {
    return <div></div>;
  }

  return (
    <div className="home-page-container">
      <div dangerouslySetInnerHTML={{ __html: html }}></div>
    </div>

  );
};

export default RulesViewer;
