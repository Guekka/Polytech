<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
      <head>
        <title>CD Catalog</title>
      </head>
      <body>
        <h1>CD Catalog</h1>
        <table border="1">
          <tr bgcolor="#9acd32">
            <th>Title</th>
            <th>Artist</th>
            <th>Country</th>
            <th>Company</th>
            <th>Price</th>
            <th>Year</th>
          </tr>
          <xsl:apply-templates select="CATALOG/CD"/>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="CD">
    <tr>
      <td><xsl:value-of select="TITLE"/></td>
      <td><xsl:value-of select="ARTIST"/></td>
      <td><xsl:value-of select="COUNTRY"/></td>
      <td><xsl:value-of select="COMPANY"/></td>
      <td><xsl:value-of select="PRICE"/></td>
      <td><xsl:value-of select="YEAR"/></td>
    </tr>
  </xsl:template>
</xsl:stylesheet>
