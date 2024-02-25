<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head> <title>Liste de titres et artistes récents</title> </head>
			<body>
				<h2>Liste de titres et artistes récents</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">Title</th>
						<th align="left">Artist</th>
					</tr>
					<xsl:for-each select="CATALOG/CD">
						<xsl:if test="@year &gt; 1990">
							<tr>
								<td><xsl:value-of select="TITLE"/></td>
								<td><xsl:value-of select="ARTIST"/></td>
							</tr>
						</xsl:if>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
