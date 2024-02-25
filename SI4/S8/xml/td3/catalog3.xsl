<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>CDs with Price Bigger Than 10€</title>
      </head>
      <body>
        <h1>CDs with Price Bigger Than 10€</h1>
        <ul>
          <xsl:apply-templates select="CATALOG/CD[PRICE > 10]"/>
        </ul>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="CD">
    <li>
      <strong>Title:</strong> <xsl:value-of select="TITLE"/><br/>
      <strong>Artist:</strong> <xsl:value-of select="ARTIST"/>
    </li>
  </xsl:template>
</xsl:stylesheet>
