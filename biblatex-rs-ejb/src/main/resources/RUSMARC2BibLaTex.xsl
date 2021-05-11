<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                extension-element-prefixes="exsl">
    <xsl:output method="xml" indent="no" standalone="no"
                encoding="utf-8" omit-xml-declaration="yes" />

    <xsl:param name="default.publisher"
               select="'Санкт-Петербургский политехнический университет Петра Великого'" />

    <xsl:template match='record'>
        <file>
            <xsl:call-template name="personalCreatorName" />
            <xsl:value-of select="subfield[@id='p']"/>
            <entry id="value" >
                <recordType>
                    <xsl:choose>
                    <xsl:when
                            test="substring(field[@id='105']/subfield[@id='a'], 5, 1) = 'f'">
                        <xsl:text>encyclopaedia</xsl:text>
                    </xsl:when>
                        <xsl:when
                                test="substring(field[@id='105']/subfield[@id='a'], 5, 1) = 'p'">
                            <xsl:text>technical report</xsl:text>
                        </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="field[@id='200']/subfield[@id='e']" />
                    </xsl:otherwise>
                    </xsl:choose>
                </recordType>

                <author>
                    <xsl:choose>
                        <xsl:when
                                test="field[@id='700' or @id='701'] or field[@id='461' or @id='463']/subfield[@id='1']/field[@id='700' or @id='701'] or field[@id='710' or @id='711'] or field[@id='461' or @id='463']/subfield[@id='1']/field[@id='710' or @id='711']">
                            <xsl:for-each
                                    select="field[@id='700' or @id='701'] | field[@id='461' or @id='463']/subfield[@id='1']/field[@id='700' or @id='701']">
                                <xsl:call-template name="personalCreator" />
                            </xsl:for-each>
                            <xsl:for-each
                                    select="field[@id='710' or @id='711'] | field[@id='461' or @id='463']/subfield[@id='1']/field[@id='710' or @id='711']">
                                <!--
                                        <xsl:call-template name="corporateCreator" />
                                        -->
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when
                                        test="field[@id='702'] or field[@id='461' or @id='463']/subfield[@id='1']/field[@id='702']">
                                    <xsl:for-each
                                            select="(field[@id='702'] | field[@id='461' or @id='463']/subfield[@id='1']/field[@id='702'])[1]">
                                        <xsl:call-template name="personalCreator" />
                                    </xsl:for-each>
                                </xsl:when>
                                <xsl:when
                                        test="field[@id='712'] or field[@id='461' or @id='463']/subfield[@id='1']/field[@id='712']">
                                    <xsl:for-each
                                            select="(field[@id='712'] | field[@id='461' or @id='463']/subfield[@id='1']/field[@id='712'])[1]">
                                        <!--
                                        <xsl:call-template name="corporateCreator" />
                                        -->
                                    </xsl:for-each>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </author>

                <title>
                    <xsl:value-of select="field[@id='200']/subfield[@id='a']" />
                    <xsl:for-each select="field[@id='200']/subfield[@id='h' or @id='i']">
                        <xsl:choose>
                            <xsl:when test="@id='h'">
                                <xsl:text>. </xsl:text>
                                <xsl:value-of select="." />
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:choose>
                                    <xsl:when test="preceding-sibling::subfield[1]/@id='h'">
                                        <xsl:text>, </xsl:text>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:text>. </xsl:text>
                                    </xsl:otherwise>
                                </xsl:choose>
                                <xsl:value-of select="." />
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </title>

                <edition>
                    <xsl:value-of select="field[@id='205']/subfield[@id='a']" />
                </edition>

                <title_chapter>
                    <xsl:value-of select="field[@id='463']/subfield[@id='1']/subfield[@id='200']/subfield[@id='a']" />
                </title_chapter>

                <journal>
                    <xsl:value-of select="field[@id='461']/subfield[@id='1']/field[@id='200']/subfield[@id='a']" />
                </journal>

                <editor>
                    <xsl:choose>
                        <xsl:when
                                test="field[@id='200']/subfield[@id='f']">
                            <xsl:value-of
                                    select="field[@id='200']/subfield[@id='f']">
                            </xsl:value-of>
                        </xsl:when>
                    </xsl:choose>
                </editor>

                <school>
                    <xsl:value-of select="field[@id='712']/subfield[@id='a']" />
                </school>

                <address>
                    <xsl:choose>
                        <xsl:when test="field[@id='210']/subfield[@id='a']">
                            <xsl:value-of select="field[@id='210']/subfield[@id='a']" />
                        </xsl:when>
                        <xsl:when test="field[@id='461' or @id = '463']/subfield[@id='1']/field[@id='210']/subfield[@id='b']">
                            <xsl:value-of select="field[@id='461' or @id = '463']/subfield[@id='1']/field[@id='210']/subfield[@id='b']" />
                        </xsl:when>
                        <xsl:when test="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='b']">
                            <xsl:value-of select="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='b']" />
                        </xsl:when>
                    </xsl:choose>
                </address>

                <publisher>
                    <xsl:choose>
                        <xsl:when test="field[@id='210']/subfield[@id='c' or @id='g']">
                            <xsl:value-of select="field[@id='210']/subfield[@id='c' or @id='g'][1]" />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when
                                        test="field[@id='461']/subfield[@id='1']/field[@id='210']/subfield[@id='c']">
                                    <xsl:value-of
                                            select="field[@id='461']/subfield[@id='1']/field[@id='210']/subfield[@id='c']" />
                                </xsl:when>
                                <xsl:when
                                        test="field[@id='461']/subfield[@id='1']/field[@id='300']/subfield[@id='a']">
                                    <xsl:value-of
                                            select="field[@id='461']/subfield[@id='1']/field[@id='300']/subfield[@id='a']" />
                                </xsl:when>
                                <xsl:when
                                        test="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='c' or @id='g']">
                                    <xsl:value-of
                                            select="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='c' or @id='g'][1]" />
                                </xsl:when>
<!--                                <xsl:when test="field[@id='210']/subfield[@id='a']">-->
<!--                                    <xsl:value-of select="field[@id='210']/subfield[@id='a'][1]" />-->
<!--                                </xsl:when>-->
                                <xsl:otherwise>
                                    <xsl:value-of select="$default.publisher" />
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </publisher>

                <year>
                    <xsl:choose>
                        <xsl:when test="field[@id='210']/subfield[@id='d']">
                            <xsl:value-of select="field[@id='210']/subfield[@id='d'][1]" />
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when
                                        test="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='d']">
                                    <xsl:value-of
                                            select="field[@id='463']/subfield[@id='1']/field[@id='210']/subfield[@id='d'][1]" />
                                </xsl:when>
<!--                                <xsl:otherwise>-->
<!--                                    <xsl:text>Unknown</xsl:text>-->
<!--                                </xsl:otherwise>-->
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </year>

                <volume>
                    <xsl:choose>
                        <xsl:when
                                test="field[@id='461']/subfield[@id='1']/field[@if='200']/subfield[@id='v']">
                            <xsl:value-of
                                    select="field[@id='461']/subfield[@id='1']/field[@if='200']/subfield[@id='v']" />
                        </xsl:when>
                        <xsl:when test="field[@id='462']/subfield[@id='1']/field[@id='200']/subfield[@id='v']">
                            <xsl:value-of select="field[@id='462']/subfield[@id='1']/field[@id='200']/subfield[@id='v']"/>
                        </xsl:when>
                        <xsl:when test="(field[@id='463']/subfield[@id='1']/field[@id='200']/subfield[@id='a'])">
                            <xsl:value-of select="field[@id='463']/subfield[@id='1']/field[@id='200']/subfield[@id='a']" />
                        </xsl:when>
<!--                        <xsl:otherwise>-->
<!--                            <xsl:text>Unknown</xsl:text>-->
<!--                        </xsl:otherwise>-->
                    </xsl:choose>
                </volume>

                <number>
                    <xsl:value-of select="field[@id='463']/subfield[@id='1']/field[@id='200']/subfield[@id='a']" />
                </number>

                <technumber>
                    <xsl:value-of select="field[@id='15']/subfield[@id='a']" />
                </technumber>

                <pages>
                    <xsl:value-of select="field[@id='215']/subfield[@id='a']" />
                </pages>

                <url>
                    <xsl:value-of select="field[@id='856']/subfield[@id='u']" />
                </url>

                <journal_description>
                    <xsl:value-of select="field[@id='461']/subfield[@id='1']/field[@id='200']/subfield[@id='e']" />
                </journal_description>
            </entry>
        </file>
    </xsl:template>



    <xsl:template name="personalCreator">
        <xsl:call-template name="personalCreatorName" />
        <xsl:for-each select="subfield[@id='p']">
            <affiliation>
                <xsl:value-of select="." />
            </affiliation>
        </xsl:for-each>
    </xsl:template>



    <xsl:template name="personalCreatorName">
        <xsl:value-of select="subfield[@id='a']" />
        <xsl:if test="subfield[@id='d']">
            <xsl:text> </xsl:text>
            <xsl:value-of select="subfield[@id='d']" />
        </xsl:if>
        <xsl:choose>
            <xsl:when test="subfield[@id='g']">
                <xsl:text>, </xsl:text>
                <xsl:value-of select="subfield[@id='g']" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="subfield[@id='b']">
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="subfield[@id='b']" />
                    <xsl:text> , </xsl:text>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="subfield[@id='c']">
            <xsl:text> (</xsl:text>
            <xsl:for-each select="subfield[@id='c']">
                <xsl:value-of select="." />
                <xsl:if test="position() != last()">
                    <xsl:text>;</xsl:text>
                </xsl:if>
            </xsl:for-each>
            <xsl:text>) </xsl:text>
        </xsl:if>
    </xsl:template>
</xsl:transform>