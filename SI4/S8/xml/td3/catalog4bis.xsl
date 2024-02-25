<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<Artistes>
			<xsl:apply-templates/>
		</Artistes>
	</xsl:template>

	<xsl:template match="CD[COUNTRY='UK']">
		<Artiste>
			<Nom>
				<xsl:value-of select="ARTIST"/>
			</Nom>
			<Pays>
				<xsl:value-of select="COUNTRY"/>
			</Pays>
		</Artiste>
	</xsl:template>

	<xsl:template match="CD"/>

</xsl:stylesheet>
