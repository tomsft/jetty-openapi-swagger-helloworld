window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  const apiUrl = window.location.origin + "/api/swagger.json"; 
  window.ui = SwaggerUIBundle({
    url: apiUrl,
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });

  //</editor-fold>
};
