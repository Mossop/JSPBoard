<%@ taglib uri="/WEB-INF/jspboard.tld" prefix="jspb" %>

<jspb:includes>
              <td width="578" valign="top">
                <table border="0">
                  <tr>
                    <td valign="top">
                      <h1>WSWYM Bulletin Board Messages</h1>
                    </td>
                    <td valign="top" align="right">
                      <a href="xdf.php?command1=view&amp;class1=board&amp;name1=folderlist&amp;command2=view&amp;class2=folder&amp;id2=47&amp;name2=folderedit&amp;folder=47&amp;stylesheet=edit&PHPSESSID=e831cf7bded8c1e6d6ff8b89eb730bb3">
									Administration
								</a>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" width="578">
                      <table border="0" cellspacing="1">
                        <tr>
                          <td width="338">
                            <b>Thread</b>
                          </td>
                          <td width="100">
                            <b>Author</b>
                          </td>
                          <td width="120" align="right">
                            <b>Started</b>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                  	<jspb:secure groups="admin">
                    <td colspan="2">
                      <hr>
                      <h2>Post a new thread:</h2>
                      <form action="xdf.php" method="post">
                        <table>
                          <tr>
                            <td>Subject:</td>
                            <td>
                              <input type="text" name="name1" size="70">
                            </td>
                          </tr>
                          <tr>
                            <td colspan="2">
                              <textarea name="content2" rows="15" cols="60"></textarea>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="2" align="center">
                              <input type="submit" value="Add">
                            </td>
                          </tr>
                        </table>
                      </form>
                    </td>
                    </jspb:secure>
                  </tr>
                  <tr>
                    <td align="center" colspan="2"></td>
                  </tr>
                </table>
</jspb:includes>
