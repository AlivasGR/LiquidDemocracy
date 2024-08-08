/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.UserDB;
import gr.uoc.csd.hy359.liquid_democracy.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alivas
 */
public class profileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String name = (String) session.getAttribute("name");
                try {
                    User u = UserDB.getUser(name);
                    //Things could be better here :( will fix i promise
                    out.println("<ul id =\"profile_list\">\n"
                            + "    <p>Welcome " + name + ". Click the parameter headers to edit them</p>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\">Username:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getUserName()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"uidform\" class = \"hiddenform\">\n"
                            + "            <input type=\"text\" placeholder=\"Enter New Username\" id = \"uid\" name=\"username\" pattern=\"[A-Za-z]{8,}\" required onblur = \"validateUser()\"/><br/><p class = \"errmsg\" id =\"uidmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"uidsub\" value =\"Confirm\" onclick =\"update({name: 'username', value: document.getElementById('uid').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('passform')\">Password:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"passform\" class = \"hiddenform\">\n"
                            + "            <input type =\"password\" id = \"pass0\" name = \"old_password\" placeholder=\"Enter Old Password\" pattern =\"(?=^.{8,10}$)(?=.*[^a-zA-Z0-9])(?=.*[a-zA-Z])(?=.*[0-9]).*$\" required onblur =\"checkOldPass()\"/><p class =\"errmsg\" id =\"pwd0msg\"></p>\n"
                            + "            <input type =\"password\" id = \"pass1\" name = \"password\" placeholder =\"Enter New Password\" pattern =\"(?=^.{8,10}$)(?=.*[^a-zA-Z0-9])(?=.*[a-zA-Z])(?=.*[0-9]).*$\" required onblur =\"validatePass()\"/><p class =\"errmsg\" id =\"pwdmsg\"></p>\n"
                            + "            <input type =\"password\" id = \"pass2\" name = \"password2\" placeholder =\"Re-enter New Password\" pattern =\"(?=^.{8,10}$)(?=.*[^a-zA-Z0-9])(?=.*[a-zA-Z])(?=.*[0-9]).*$\" required onblur =\"checkPass()\"/><p class =\"errmsg\" id =\"pwd2msg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"pwdsub\" value =\"Confirm\" onclick =\"update({name: 'password', value: document.getElementById('pass1').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('emailform')\">e-mail:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getEmail()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"emailform\" class = \"hiddenform\">\n"
                            + "            <input type=\"email\" placeholder=\"Enter New Email\" id = \"em\" name=\"email\" required onblur = \"validateEmail()\"/><br/><p class = \"errmsg\" id =\"emlmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"emlsub\" value =\"Confirm\" onclick =\"update({name: 'email', value: document.getElementById('em').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('fnameform')\">First Name:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getFirstName()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"fnameform\" class = \"hiddenform\">\n"
                            + "            <input type=\"text\" placeholder=\"Enter New First Name\" id = \"fname\" name=\"firstname\" maxlength =\"20\" required onblur = \"validateFnm()\"/><br/><p class = \"errmsg\" id =\"fnmmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"fnsub\" value =\"Confirm\" onclick =\"update({name: 'firstname', value: document.getElementById('fname').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('lnameform')\">Last Name:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getLastName()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"lnameform\" class = \"hiddenform\">\n"
                            + "            <input type=\"text\" placeholder=\"Enter New Last Name\" id =\"lname\" name=\"lastname\" pattern = \"[\\w]{4,20}\" required onblur = \"validateLnm()\"/><br/><p class = \"errmsg\" id =\"lnmmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"lnsub\" value =\"Confirm\" onclick =\"update({name: 'lastname', value: document.getElementById('lname').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('ocpform')\">Occupation:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getOccupation()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"ocpform\" class = \"hiddenform\">\n"
                            + "            <input type =\"text\" placeholder =\"Enter New Occupation\" pattern = \"[\\w]{2,20}\" id = \"ocp\" name =\"occupation\" required onblur = \"validateOccupation()\"/><br/><p class = \"errmsg\" id =\"occmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"ocpsub\" value =\"Confirm\" onclick =\"update({name: 'occupation', value: document.getElementById('ocp').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('ctrform')\">Country:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getCountry()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"ctrform\" class = \"hiddenform\">\n"
                            + "            <select id = \"ctr\" name = \"country\" onblur = \"geocodeAddress()\">\n"
                            + "                <option value=\"AF\">Afghanistan</option>\n"
                            + "                <option value=\"AX\">Åland Islands</option>\n"
                            + "                <option value=\"AL\">Albania</option>\n"
                            + "                <option value=\"DZ\">Algeria</option>\n"
                            + "                <option value=\"AS\">American Samoa</option>\n"
                            + "                <option value=\"AD\">Andorra</option>\n"
                            + "                <option value=\"AO\">Angola</option>\n"
                            + "                <option value=\"AI\">Anguilla</option>\n"
                            + "                <option value=\"AQ\">Antarctica</option>\n"
                            + "                <option value=\"AG\">Antigua and Barbuda</option>\n"
                            + "                <option value=\"AR\">Argentina</option>\n"
                            + "                <option value=\"AM\">Armenia</option>\n"
                            + "                <option value=\"AW\">Aruba</option>\n"
                            + "                <option value=\"AU\">Australia</option>\n"
                            + "                <option value=\"AT\">Austria</option>\n"
                            + "                <option value=\"AZ\">Azerbaijan</option>\n"
                            + "                <option value=\"BS\">Bahamas</option>\n"
                            + "                <option value=\"BH\">Bahrain</option>\n"
                            + "                <option value=\"BD\">Bangladesh</option>\n"
                            + "                <option value=\"BB\">Barbados</option>\n"
                            + "                <option value=\"BY\">Belarus</option>\n"
                            + "                <option value=\"BE\">Belgium</option>\n"
                            + "                <option value=\"BZ\">Belize</option>\n"
                            + "                <option value=\"BJ\">Benin</option>\n"
                            + "                <option value=\"BM\">Bermuda</option>\n"
                            + "                <option value=\"BT\">Bhutan</option>\n"
                            + "                <option value=\"BO\">Bolivia, Plurinational State of</option>\n"
                            + "                <option value=\"BQ\">Bonaire, Sint Eustatius and Saba</option>\n"
                            + "                <option value=\"BA\">Bosnia and Herzegovina</option>\n"
                            + "                <option value=\"BW\">Botswana</option>\n"
                            + "                <option value=\"BV\">Bouvet Island</option>\n"
                            + "                <option value=\"BR\">Brazil</option>\n"
                            + "                <option value=\"IO\">British Indian Ocean Territory</option>\n"
                            + "                <option value=\"BN\">Brunei Darussalam</option>\n"
                            + "                <option value=\"BG\">Bulgaria</option>\n"
                            + "                <option value=\"BF\">Burkina Faso</option>\n"
                            + "                <option value=\"BI\">Burundi</option>\n"
                            + "                <option value=\"KH\">Cambodia</option>\n"
                            + "                <option value=\"CM\">Cameroon</option>\n"
                            + "                <option value=\"CA\">Canada</option>\n"
                            + "                <option value=\"CV\">Cape Verde</option>\n"
                            + "                <option value=\"KY\">Cayman Islands</option>\n"
                            + "                <option value=\"CF\">Central African Republic</option>\n"
                            + "                <option value=\"TD\">Chad</option>\n"
                            + "                <option value=\"CL\">Chile</option>\n"
                            + "                <option value=\"CN\">China</option>\n"
                            + "                <option value=\"CX\">Christmas Island</option>\n"
                            + "                <option value=\"CC\">Cocos (Keeling) Islands</option>\n"
                            + "                <option value=\"CO\">Colombia</option>\n"
                            + "                <option value=\"KM\">Comoros</option>\n"
                            + "                <option value=\"CG\">Congo</option>\n"
                            + "                <option value=\"CD\">Congo, the Democratic Republic of the</option>\n"
                            + "                <option value=\"CK\">Cook Islands</option>\n"
                            + "                <option value=\"CR\">Costa Rica</option>\n"
                            + "                <option value=\"CI\">Côte d'Ivoire</option>\n"
                            + "                <option value=\"HR\">Croatia</option>\n"
                            + "                <option value=\"CU\">Cuba</option>\n"
                            + "                <option value=\"CW\">Curaçao</option>\n"
                            + "                <option value=\"CY\">Cyprus</option>\n"
                            + "                <option value=\"CZ\">Czech Republic</option>\n"
                            + "                <option value=\"DK\">Denmark</option>\n"
                            + "                <option value=\"DJ\">Djibouti</option>\n"
                            + "                <option value=\"DM\">Dominica</option>\n"
                            + "                <option value=\"DO\">Dominican Republic</option>\n"
                            + "                <option value=\"EC\">Ecuador</option>\n"
                            + "                <option value=\"EG\">Egypt</option>\n"
                            + "                <option value=\"SV\">El Salvador</option>\n"
                            + "                <option value=\"GQ\">Equatorial Guinea</option>\n"
                            + "                <option value=\"ER\">Eritrea</option>\n"
                            + "                <option value=\"EE\">Estonia</option>\n"
                            + "                <option value=\"ET\">Ethiopia</option>\n"
                            + "                <option value=\"FK\">Falkland Islands (Malvinas)</option>\n"
                            + "                <option value=\"FO\">Faroe Islands</option>\n"
                            + "                <option value=\"FJ\">Fiji</option>\n"
                            + "                <option value=\"FI\">Finland</option>\n"
                            + "                <option value=\"FR\">France</option>\n"
                            + "                <option value=\"GF\">French Guiana</option>\n"
                            + "                <option value=\"PF\">French Polynesia</option>\n"
                            + "                <option value=\"TF\">French Southern Territories</option>\n"
                            + "                <option value=\"GA\">Gabon</option>\n"
                            + "                <option value=\"GM\">Gambia</option>\n"
                            + "                <option value=\"GE\">Georgia</option>\n"
                            + "                <option value=\"DE\">Germany</option>\n"
                            + "                <option value=\"GH\">Ghana</option>\n"
                            + "                <option value=\"GI\">Gibraltar</option>\n"
                            + "                <option value=\"GR\" selected>Greece</option>\n"
                            + "                <option value=\"GL\">Greenland</option>\n"
                            + "                <option value=\"GD\">Grenada</option>\n"
                            + "                <option value=\"GP\">Guadeloupe</option>\n"
                            + "                <option value=\"GU\">Guam</option>\n"
                            + "                <option value=\"GT\">Guatemala</option>\n"
                            + "                <option value=\"GG\">Guernsey</option>\n"
                            + "                <option value=\"GN\">Guinea</option>\n"
                            + "                <option value=\"GW\">Guinea-Bissau</option>\n"
                            + "                <option value=\"GY\">Guyana</option>\n"
                            + "                <option value=\"HT\">Haiti</option>\n"
                            + "                <option value=\"HM\">Heard Island and McDonald Islands</option>\n"
                            + "                <option value=\"VA\">Holy See (Vatican City State)</option>\n"
                            + "                <option value=\"HN\">Honduras</option>\n"
                            + "                <option value=\"HK\">Hong Kong</option>\n"
                            + "                <option value=\"HU\">Hungary</option>\n"
                            + "                <option value=\"IS\">Iceland</option>\n"
                            + "                <option value=\"IN\">India</option>\n"
                            + "                <option value=\"ID\">Indonesia</option>\n"
                            + "                <option value=\"IR\">Iran, Islamic Republic of</option>\n"
                            + "                <option value=\"IQ\">Iraq</option>\n"
                            + "                <option value=\"IE\">Ireland</option>\n"
                            + "                <option value=\"IM\">Isle of Man</option>\n"
                            + "                <option value=\"IL\">Israel</option>\n"
                            + "                <option value=\"IT\">Italy</option>\n"
                            + "                <option value=\"JM\">Jamaica</option>\n"
                            + "                <option value=\"JP\">Japan</option>\n"
                            + "                <option value=\"JE\">Jersey</option>\n"
                            + "                <option value=\"JO\">Jordan</option>\n"
                            + "                <option value=\"KZ\">Kazakhstan</option>\n"
                            + "                <option value=\"KE\">Kenya</option>\n"
                            + "                <option value=\"KI\">Kiribati</option>\n"
                            + "                <option value=\"KP\">Korea, Democratic People's Republic of</option>\n"
                            + "                <option value=\"KR\">Korea, Republic of</option>\n"
                            + "                <option value=\"KW\">Kuwait</option>\n"
                            + "                <option value=\"KG\">Kyrgyzstan</option>\n"
                            + "                <option value=\"LA\">Lao People's Democratic Republic</option>\n"
                            + "                <option value=\"LV\">Latvia</option>\n"
                            + "                <option value=\"LB\">Lebanon</option>\n"
                            + "                <option value=\"LS\">Lesotho</option>\n"
                            + "                <option value=\"LR\">Liberia</option>\n"
                            + "                <option value=\"LY\">Libya</option>\n"
                            + "                <option value=\"LI\">Liechtenstein</option>\n"
                            + "                <option value=\"LT\">Lithuania</option>\n"
                            + "                <option value=\"LU\">Luxembourg</option>\n"
                            + "                <option value=\"MO\">Macao</option>\n"
                            + "                <option value=\"MK\">Macedonia, the former Yugoslav Republic of</option>\n"
                            + "                <option value=\"MG\">Madagascar</option>\n"
                            + "                <option value=\"MW\">Malawi</option>\n"
                            + "                <option value=\"MY\">Malaysia</option>\n"
                            + "                <option value=\"MV\">Maldives</option>\n"
                            + "                <option value=\"ML\">Mali</option>\n"
                            + "                <option value=\"MT\">Malta</option>\n"
                            + "                <option value=\"MH\">Marshall Islands</option>\n"
                            + "                <option value=\"MQ\">Martinique</option>\n"
                            + "                <option value=\"MR\">Mauritania</option>\n"
                            + "                <option value=\"MU\">Mauritius</option>\n"
                            + "                <option value=\"YT\">Mayotte</option>\n"
                            + "                <option value=\"MX\">Mexico</option>\n"
                            + "                <option value=\"FM\">Micronesia, Federated States of</option>\n"
                            + "                <option value=\"MD\">Moldova, Republic of</option>\n"
                            + "                <option value=\"MC\">Monaco</option>\n"
                            + "                <option value=\"MN\">Mongolia</option>\n"
                            + "                <option value=\"ME\">Montenegro</option>\n"
                            + "                <option value=\"MS\">Montserrat</option>\n"
                            + "                <option value=\"MA\">Morocco</option>\n"
                            + "                <option value=\"MZ\">Mozambique</option>\n"
                            + "                <option value=\"MM\">Myanmar</option>\n"
                            + "                <option value=\"NA\">Namibia</option>\n"
                            + "                <option value=\"NR\">Nauru</option>\n"
                            + "                <option value=\"NP\">Nepal</option>\n"
                            + "                <option value=\"NL\">Netherlands</option>\n"
                            + "                <option value=\"NC\">New Caledonia</option>\n"
                            + "                <option value=\"NZ\">New Zealand</option>\n"
                            + "                <option value=\"NI\">Nicaragua</option>\n"
                            + "                <option value=\"NE\">Niger</option>\n"
                            + "                <option value=\"NG\">Nigeria</option>\n"
                            + "                <option value=\"NU\">Niue</option>\n"
                            + "                <option value=\"NF\">Norfolk Island</option>\n"
                            + "                <option value=\"MP\">Northern Mariana Islands</option>\n"
                            + "                <option value=\"NO\">Norway</option>\n"
                            + "                <option value=\"OM\">Oman</option>\n"
                            + "                <option value=\"PK\">Pakistan</option>\n"
                            + "                <option value=\"PW\">Palau</option>\n"
                            + "                <option value=\"PS\">Palestinian Territory, Occupied</option>\n"
                            + "                <option value=\"PA\">Panama</option>\n"
                            + "                <option value=\"PG\">Papua New Guinea</option>\n"
                            + "                <option value=\"PY\">Paraguay</option>\n"
                            + "                <option value=\"PE\">Peru</option>\n"
                            + "                <option value=\"PH\">Philippines</option>\n"
                            + "                <option value=\"PN\">Pitcairn</option>\n"
                            + "                <option value=\"PL\">Poland</option>\n"
                            + "                <option value=\"PT\">Portugal</option>\n"
                            + "                <option value=\"PR\">Puerto Rico</option>\n"
                            + "                <option value=\"QA\">Qatar</option>\n"
                            + "                <option value=\"RE\">Réunion</option>\n"
                            + "                <option value=\"RO\">Romania</option>\n"
                            + "                <option value=\"RU\">Russian Federation</option>\n"
                            + "                <option value=\"RW\">Rwanda</option>\n"
                            + "                <option value=\"BL\">Saint Barthélemy</option>\n"
                            + "                <option value=\"SH\">Saint Helena, Ascension and Tristan da Cunha</option>\n"
                            + "                <option value=\"KN\">Saint Kitts and Nevis</option>\n"
                            + "                <option value=\"LC\">Saint Lucia</option>\n"
                            + "                <option value=\"MF\">Saint Martin (French part)</option>\n"
                            + "                <option value=\"PM\">Saint Pierre and Miquelon</option>\n"
                            + "                <option value=\"VC\">Saint Vincent and the Grenadines</option>\n"
                            + "                <option value=\"WS\">Samoa</option>\n"
                            + "                <option value=\"SM\">San Marino</option>\n"
                            + "                <option value=\"ST\">Sao Tome and Principe</option>\n"
                            + "                <option value=\"SA\">Saudi Arabia</option>\n"
                            + "                <option value=\"SN\">Senegal</option>\n"
                            + "                <option value=\"RS\">Serbia</option>\n"
                            + "                <option value=\"SC\">Seychelles</option>\n"
                            + "                <option value=\"SL\">Sierra Leone</option>\n"
                            + "                <option value=\"SG\">Singapore</option>\n"
                            + "                <option value=\"SX\">Sint Maarten (Dutch part)</option>\n"
                            + "                <option value=\"SK\">Slovakia</option>\n"
                            + "                <option value=\"SI\">Slovenia</option>\n"
                            + "                <option value=\"SB\">Solomon Islands</option>\n"
                            + "                <option value=\"SO\">Somalia</option>\n"
                            + "                <option value=\"ZA\">South Africa</option>\n"
                            + "                <option value=\"GS\">South Georgia and the South Sandwich Islands</option>\n"
                            + "                <option value=\"SS\">South Sudan</option>\n"
                            + "                <option value=\"ES\">Spain</option>\n"
                            + "                <option value=\"LK\">Sri Lanka</option>\n"
                            + "                <option value=\"SD\">Sudan</option>\n"
                            + "                <option value=\"SR\">Suriname</option>\n"
                            + "                <option value=\"SJ\">Svalbard and Jan Mayen</option>\n"
                            + "                <option value=\"SZ\">Swaziland</option>\n"
                            + "                <option value=\"SE\">Sweden</option>\n"
                            + "                <option value=\"CH\">Switzerland</option>\n"
                            + "                <option value=\"SY\">Syrian Arab Republic</option>\n"
                            + "                <option value=\"TW\">Taiwan, Province of China</option>\n"
                            + "                <option value=\"TJ\">Tajikistan</option>\n"
                            + "                <option value=\"TZ\">Tanzania, United Republic of</option>\n"
                            + "                <option value=\"TH\">Thailand</option>\n"
                            + "                <option value=\"TL\">Timor-Leste</option>\n"
                            + "                <option value=\"TG\">Togo</option>\n"
                            + "                <option value=\"TK\">Tokelau</option>\n"
                            + "                <option value=\"TO\">Tonga</option>\n"
                            + "                <option value=\"TT\">Trinidad and Tobago</option>\n"
                            + "                <option value=\"TN\">Tunisia</option>\n"
                            + "                <option value=\"TR\">Turkey</option>\n"
                            + "                <option value=\"TM\">Turkmenistan</option>\n"
                            + "                <option value=\"TC\">Turks and Caicos Islands</option>\n"
                            + "                <option value=\"TV\">Tuvalu</option>\n"
                            + "                <option value=\"UG\">Uganda</option>\n"
                            + "                <option value=\"UA\">Ukraine</option>\n"
                            + "                <option value=\"AE\">United Arab Emirates</option>\n"
                            + "                <option value=\"GB\">United Kingdom</option>\n"
                            + "                <option value=\"US\">United States</option>\n"
                            + "                <option value=\"UM\">United States Minor Outlying Islands</option>\n"
                            + "                <option value=\"UY\">Uruguay</option>\n"
                            + "                <option value=\"UZ\">Uzbekistan</option>\n"
                            + "                <option value=\"VU\">Vanuatu</option>\n"
                            + "                <option value=\"VE\">Venezuela, Bolivarian Republic of</option>\n"
                            + "                <option value=\"VN\">Viet Nam</option>\n"
                            + "                <option value=\"VG\">Virgin Islands, British</option>\n"
                            + "                <option value=\"VI\">Virgin Islands, U.S.</option>\n"
                            + "                <option value=\"WF\">Wallis and Futuna</option>\n"
                            + "                <option value=\"EH\">Western Sahara</option>\n"
                            + "                <option value=\"YE\">Yemen</option>\n"
                            + "                <option value=\"ZM\">Zambia</option>\n"
                            + "                <option value=\"ZW\">Zimbabwe</option>\n"
                            + "            </select><br/>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"ccasub\" value =\"Confirm\" onclick =\n"
                            + "                   \"update({name: 'country', value: document.getElementById('ctr').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('cityform')\">City:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getTown()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"cityform\" class = \"hiddenform\">\n"
                            + "            <input type =\"text\" placeholder =\"Enter New City\" pattern =\"[\\w]{2,20}\" id = \"ct\" name =\"city\" required onblur = \"validateCity(); geocodeAddress();\"/><br/><p class = \"errmsg\" id =\"citmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"ccasub\" value =\"Confirm\" onclick =\n"
                            + "                   \"update({name: 'city', value: document.getElementById('ct').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('adrform')\">Address:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getAddress()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"adrform\" class = \"hiddenform\">\n"
                            + "            <input type =\"text\" placeholder =\"Enter New Address\" id = \"adr\" name =\"address\" onblur = \"geocodeAddress()\"/><br/><p class = \"errmsg\" id =\"adrmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"ccasub\" value =\"Confirm\" onclick = \n"
                            + "                   \"update({name: 'address', value: document.getElementById('adr').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('dobform')\">Date of Birth:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getBirthDate()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"dobform\" class = \"hiddenform\">\n"
                            + "            <input type=\"date\" id = \"bd\" name=\"bday\" required pattern = \"([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\" onblur = \"validateDate()\"/><br/><p class =\"errmsg\" id =\"dtmsg\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"dobsub\" value =\"Confirm\" onclick =\"update({name: 'dateOfBirth', value: document.getElementById('bd').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('sexform')\">Gender:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getGender()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"sexform\" class = \"hiddenform\">\n"
                            + "            <div id =\"Gender\">\n"
                            + "                <span class = \"genderbutton\"><input type=\"radio\" name=\"gender\" id =\"male_button\" value=\"male\" checked/>Male</span>\n"
                            + "                <span class = \"genderbutton\"><input type=\"radio\" name=\"gender\" id =\"female_button\" value=\"female\"/>Female</span>\n"
                            + "                <span class = \"genderbutton\"><input type=\"radio\" name=\"gender\" id =\"other_button\" value=\"other\"/>Other</span>\n"
                            + "            </div>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"sexsub\" value =\"Confirm\" onclick =\"update({name: 'gender', value:\n"
                            + "                                document.getElementById('male_button').checked === true ? document.getElementById('male_button').value :\n"
                            + "                                (document.getElementById('female_button') === true ? document.getElementById('female_button').value :\n"
                            + "                                        document.getElementById('other_button').value)})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('intrform')\">Interests:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getInterests()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"intrform\" class = \"hiddenform\">\n"
                            + "            <textarea id = \"intr\" name=\"interests\" maxlength=\"100\"></textarea><br/><p class = \"errmsg\" id =\"intmsg\" onblur = \"validateInterests()\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"intrsub\" value =\"Confirm\" onclick =\"update({name: 'interests', value: document.getElementById('intr').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "    <li class =\"profile_list_inner\">\n"
                            + "        <h3 class =\"profile_list_header\" onclick = \"toggler('genform')\">General Information:</h3>\n"
                            + "        <ul class =\"profile_list_content\">\n"
                            + "            <li>\n"
                            + u.getInfo()
                            + "            </li>\n"
                            + "        </ul>\n"
                            + "        <form id = \"genform\" class = \"hiddenform\">\n"
                            + "            <textarea id = \"gen\" name=\"generalinfo\" maxlength=\"500\"></textarea><br/><p class = \"errmsg\" id =\"genmsg\" onblur = \"validateGeneral()\"></p>\n"
                            + "            <input type =\"button\" name =\"submitbutton\" id = \"gensub\" value =\"Confirm\" onclick =\"update({name: 'general', value: document.getElementById('gen').value})\"/>\n"
                            + "        </form>\n"
                            + "    </li>\n"
                            + "</ul>");
                } catch (ClassNotFoundException e) {
                    response.setStatus(500);
                    response.resetBuffer();
                    out.println("Internal Server Error");
                }
            } else {
                out.print("<p>Please login first</p>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
