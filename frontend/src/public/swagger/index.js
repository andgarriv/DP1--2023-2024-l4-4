import { useEffect, useState } from "react";
import SwaggerUI from "swagger-ui-react";
import "swagger-ui-react/swagger-ui.css";

export default function SwaggerDocs() {
    const [docs, setDocs] = useState({});
    useEffect(() => {
        loadDocs();
    }, []);

    async function loadDocs() {
        try {
            const mydocs = await (await fetch(`/v3/api-docs`, {
                headers: {
                    "Content-Type": "application/json",
                },
            })).json();
            setDocs(mydocs);
        } catch (error) {
            console.error("Error loading Swagger docs:", error);
        }
    }

    return (
        <div style={{ overflow: "auto", height: "100vh" }}>
            <SwaggerUI spec={docs} url="" />
        </div>
    );
}
