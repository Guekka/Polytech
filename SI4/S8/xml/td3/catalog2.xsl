<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <artists>
      <xsl:apply-templates select="CATALOG/CD"/>
    </artists>
  </xsl:template>

  <xsl:template match="CD">
    <artist>
      <name><xsl:value-of select="ARTIST"/></name>
      <country><xsl:value-of select="COUNTRY"/></country>
    </artist>
  </xsl:template>
</xsl:stylesheet>
