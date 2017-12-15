package models.util;

import models.storage.DateTime;
import models.storage.User;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

import java.text.SimpleDateFormat;

public class MailService {
    private MailerClient mailerClient;

    public MailService(MailerClient mailerClient){
        this.mailerClient = mailerClient;
    }

    public void sendSignUpConfirmation(User user, String trainingName, DateTime dt, String location, String materials) {
        String cid = "1234";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Email email = new Email()
                .setSubject("Inschrijving Training")
                .setFrom("pts4.demo@gmail.com")
                .addTo(user.getEmail())
                // sends text, HTML or both...
                .setBodyHtml("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
                        "    <!--[if gte mso 9]><xml>\n" +
                        "     <o:OfficeDocumentSettings>\n" +
                        "      <o:AllowPNG/>\n" +
                        "      <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "     </o:OfficeDocumentSettings>\n" +
                        "    </xml><![endif]-->\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                        "    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                        "    <title></title>\n" +
                        "    \n" +
                        "    \n" +
                        "    <style type=\"text/css\" id=\"media-query\">\n" +
                        "      body {\n" +
                        "  margin: 0;\n" +
                        "  padding: 0; }\n" +
                        "\n" +
                        "table, tr, td {\n" +
                        "  vertical-align: top;\n" +
                        "  border-collapse: collapse; }\n" +
                        "\n" +
                        ".ie-browser table, .mso-container table {\n" +
                        "  table-layout: fixed; }\n" +
                        "\n" +
                        "* {\n" +
                        "  line-height: inherit; }\n" +
                        "\n" +
                        "a[x-apple-data-detectors=true] {\n" +
                        "  color: inherit !important;\n" +
                        "  text-decoration: none !important; }\n" +
                        "\n" +
                        "[owa] .img-container div, [owa] .img-container button {\n" +
                        "  display: block !important; }\n" +
                        "\n" +
                        "[owa] .fullwidth button {\n" +
                        "  width: 100% !important; }\n" +
                        "\n" +
                        "[owa] .block-grid .col {\n" +
                        "  display: table-cell;\n" +
                        "  float: none !important;\n" +
                        "  vertical-align: top; }\n" +
                        "\n" +
                        ".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {\n" +
                        "  width: 500px !important; }\n" +
                        "\n" +
                        ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {\n" +
                        "  line-height: 100%; }\n" +
                        "\n" +
                        ".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {\n" +
                        "  width: 164px !important; }\n" +
                        "\n" +
                        ".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {\n" +
                        "  width: 328px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {\n" +
                        "  width: 250px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {\n" +
                        "  width: 166px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {\n" +
                        "  width: 125px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {\n" +
                        "  width: 100px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {\n" +
                        "  width: 83px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {\n" +
                        "  width: 71px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {\n" +
                        "  width: 62px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {\n" +
                        "  width: 55px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {\n" +
                        "  width: 50px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {\n" +
                        "  width: 45px !important; }\n" +
                        "\n" +
                        ".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {\n" +
                        "  width: 41px !important; }\n" +
                        "\n" +
                        "@media only screen and (min-width: 520px) {\n" +
                        "  .block-grid {\n" +
                        "    width: 500px !important; }\n" +
                        "  .block-grid .col {\n" +
                        "    vertical-align: top; }\n" +
                        "    .block-grid .col.num12 {\n" +
                        "      width: 500px !important; }\n" +
                        "  .block-grid.mixed-two-up .col.num4 {\n" +
                        "    width: 164px !important; }\n" +
                        "  .block-grid.mixed-two-up .col.num8 {\n" +
                        "    width: 328px !important; }\n" +
                        "  .block-grid.two-up .col {\n" +
                        "    width: 250px !important; }\n" +
                        "  .block-grid.three-up .col {\n" +
                        "    width: 166px !important; }\n" +
                        "  .block-grid.four-up .col {\n" +
                        "    width: 125px !important; }\n" +
                        "  .block-grid.five-up .col {\n" +
                        "    width: 100px !important; }\n" +
                        "  .block-grid.six-up .col {\n" +
                        "    width: 83px !important; }\n" +
                        "  .block-grid.seven-up .col {\n" +
                        "    width: 71px !important; }\n" +
                        "  .block-grid.eight-up .col {\n" +
                        "    width: 62px !important; }\n" +
                        "  .block-grid.nine-up .col {\n" +
                        "    width: 55px !important; }\n" +
                        "  .block-grid.ten-up .col {\n" +
                        "    width: 50px !important; }\n" +
                        "  .block-grid.eleven-up .col {\n" +
                        "    width: 45px !important; }\n" +
                        "  .block-grid.twelve-up .col {\n" +
                        "    width: 41px !important; } }\n" +
                        "\n" +
                        "@media (max-width: 520px) {\n" +
                        "  .block-grid, .col {\n" +
                        "    min-width: 320px !important;\n" +
                        "    max-width: 100% !important;\n" +
                        "    display: block !important; }\n" +
                        "  .block-grid {\n" +
                        "    width: calc(100% - 40px) !important; }\n" +
                        "  .col {\n" +
                        "    width: 100% !important; }\n" +
                        "    .col > div {\n" +
                        "      margin: 0 auto; }\n" +
                        "  img.fullwidth, img.fullwidthOnMobile {\n" +
                        "    max-width: 100% !important; }\n" +
                        "  .no-stack .col {\n" +
                        "    min-width: 0 !important;\n" +
                        "    display: table-cell !important; }\n" +
                        "  .no-stack.two-up .col {\n" +
                        "    width: 50% !important; }\n" +
                        "  .no-stack.mixed-two-up .col.num4 {\n" +
                        "    width: 33% !important; }\n" +
                        "  .no-stack.mixed-two-up .col.num8 {\n" +
                        "    width: 66% !important; }\n" +
                        "  .no-stack.three-up .col.num4 {\n" +
                        "    width: 33% !important; }\n" +
                        "  .no-stack.four-up .col.num3 {\n" +
                        "    width: 25% !important; } }\n" +
                        "\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">\n" +
                        "  <style type=\"text/css\" id=\"media-query-bodytag\">\n" +
                        "    @media (max-width: 520px) {\n" +
                        "      .block-grid {\n" +
                        "        min-width: 320px!important;\n" +
                        "        max-width: 100%!important;\n" +
                        "        width: 100%!important;\n" +
                        "        display: block!important;\n" +
                        "      }\n" +
                        "\n" +
                        "      .col {\n" +
                        "        min-width: 320px!important;\n" +
                        "        max-width: 100%!important;\n" +
                        "        width: 100%!important;\n" +
                        "        display: block!important;\n" +
                        "      }\n" +
                        "\n" +
                        "        .col > div {\n" +
                        "          margin: 0 auto;\n" +
                        "        }\n" +
                        "\n" +
                        "      img.fullwidth {\n" +
                        "        max-width: 100%!important;\n" +
                        "      }\n" +
                        "\t\t\timg.fullwidthOnMobile {\n" +
                        "        max-width: 100%!important;\n" +
                        "      }\n" +
                        "      .no-stack .col {\n" +
                        "\t\t\t\tmin-width: 0!important;\n" +
                        "\t\t\t\tdisplay: table-cell!important;\n" +
                        "\t\t\t}\n" +
                        "\t\t\t.no-stack.two-up .col {\n" +
                        "\t\t\t\twidth: 50%!important;\n" +
                        "\t\t\t}\n" +
                        "\t\t\t.no-stack.mixed-two-up .col.num4 {\n" +
                        "\t\t\t\twidth: 33%!important;\n" +
                        "\t\t\t}\n" +
                        "\t\t\t.no-stack.mixed-two-up .col.num8 {\n" +
                        "\t\t\t\twidth: 66%!important;\n" +
                        "\t\t\t}\n" +
                        "\t\t\t.no-stack.three-up .col.num4 {\n" +
                        "\t\t\t\twidth: 33%!important\n" +
                        "\t\t\t}\n" +
                        "\t\t\t.no-stack.four-up .col.num3 {\n" +
                        "\t\t\t\twidth: 25%!important\n" +
                        "\t\t\t}\n" +
                        "    }\n" +
                        "  </style>\n" +
                        "  <!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                        "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                        "  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "\t<tbody>\n" +
                        "\t<tr style=\"vertical-align: top\">\n" +
                        "\t\t<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                        "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->\n" +
                        "\n" +
                        "    <div style=\"background-color:transparent;\">\n" +
                        "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 500px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">\n" +
                        "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">\n" +
                        "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 500px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->\n" +
                        "\n" +
                        "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"500\" style=\" width:500px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                        "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 500px;display: table-cell;vertical-align: top;\">\n" +
                        "              <div style=\"background-color: transparent; width: 100% !important;\">\n" +
                        "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->\n" +
                        "\n" +
                        "                  \n" +
                        "                    <div align=\"center\" class=\"img-container center  autowidth  fullwidth\" style=\"padding-right: 0px;  padding-left: 0px;\">\n" +
                        "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->\n" +
                        "<!--[if mso]></td></tr></table><![endif]-->\n" +
                        "</div>\n" +
                        "\n" +
                        "                  \n" +
                        "                  \n" +
                        "                    <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\"><![endif]-->\n" +
                        "<div style=\"color:#555555;line-height:120%;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px;\">\t\n" +
                        "\t<div style=\"font-size:12px;line-height:14px;color:#555555;font-family:Arial, 'Helvetica Neue', Helvetica, sans-serif;text-align:left;\"><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Beste "  + user.getFirstName() + ",</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">&#160;<br></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Bedankt voor het inschrijven, hieronder vindt u alle informatie nog een keer op een rijtje:</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">&#160;<br></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Naam :&#160; &#160;" + trainingName + "</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Datum:&#160; &#160;" + dt.getDateString().substring(6) + "</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Starttijd: &#160;" +  dt.getDateString().substring(0, 5)+ "</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Locatie:&#160; &#160;" +location+ "</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Benodigde materialen:"+ materials+ "</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">&#160;<br></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Wij wensen u veel succes.</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">&#160;<br></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Voor contact informatie en eventuele vragen of opemerkingen kunt u hier terecht.</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\"><br data-mce-bogus=\"1\"></p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Vriedenlijke groeten,</p><p style=\"margin: 0;font-size: 14px;line-height: 17px\">Het Info Support team</p></div>\t\n" +
                        "</div>\n" +
                        "<!--[if mso]></td></tr></table><![endif]-->\n" +
                        "\n" +
                        "                  \n" +
                        "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                        "              </div>\n" +
                        "            </div>\n" +
                        "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                        "        </div>\n" +
                        "      </div>\n" +
                        "    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                        "\t\t</td>\n" +
                        "  </tr>\n" +
                        "  </tbody>\n" +
                        "  </table>\n" +
                        "  <!--[if (mso)|(IE)]></div><![endif]-->\n" +
                        "\n" +
                        "\n" +
                        "</body></html>")
                        .setBodyText("Dit is een test email, als u niet weet waarvan deze afkomstig is kunt u deze verwijderen, excuses voor het ongemak");
        mailerClient.send(email);
    }
}