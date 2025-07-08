#!/bin/bash

REPORT_DIR="target"
INDEX_FILE="$REPORT_DIR/index.html"

mkdir -p "$REPORT_DIR"

cat > "$INDEX_FILE" <<EOF
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes de Api Usuarios</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 2rem;
        }
        h1 {
            color: #333;
        }
        .report-link {
            display: block;
            margin: 1rem 0;
            font-size: 1.2rem;
            color: #0077cc;
            text-decoration: none;
        }
        .report-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Reportes de Tests y Cobertura</h1>
    <a class="report-link" href="site/surefire-report.html" target="_blank">✅ Reporte de tests unitarios - Surefire</a>
    <a class="report-link" href="site/jacoco/index.html" target="_blank">📊 Reporte de cobertura - JaCoCo</a>
    <a class="report-link" href="karate-reports/karate-summary.html" target="_blank">🧪 Reporte de tests de aceptación - Karate</a>
</body>
</html>
EOF

echo "✅ index.html generado en $INDEX_FILE"
echo "🔗 Abrir en navegador: file://$(cd "$(dirname "$INDEX_FILE")"; pwd)/$(basename "$INDEX_FILE")"