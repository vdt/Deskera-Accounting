/*
 * Copyright (C) 2012  Krawler Information Systems Pvt Ltd
 * All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.krawler.spring.accounting.customer;

import com.krawler.common.service.ServiceException;
import com.krawler.spring.accounting.account.accAccountDAO;
import com.krawler.spring.accounting.handler.AccountingHandlerDAO;
import com.krawler.spring.auditTrailModule.auditTrailDAO;
import com.krawler.spring.common.KwlReturnObject;
import com.krawler.spring.exportFunctionality.exportMPXDAOImpl;
import com.krawler.spring.importFunctionality.ImportHandler;
import com.krawler.utils.json.base.JSONArray;
import com.krawler.utils.json.base.JSONException;
import com.krawler.utils.json.base.JSONObject;
import com.mchange.util.AssertException;
import java.util.HashMap;
import org.hibernate.SessionFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author sagar
 */
public class accCustomerControllerTest extends AbstractTransactionalDataSourceSpringContextTests {
    private SessionFactory sessionFactory = null;
    private accAccountDAO accAccountDAOObj;
    private auditTrailDAO auditTrailDAOObj;
    private accCustomerDAO accCustomerDAOObj;
    private AccountingHandlerDAO accountingHandlerDAOObj;
    private exportMPXDAOImpl exportMPXDAOImplObj;
    private ImportHandler importHandlerObj;

    //company id for demo
    private static String companyid = "a4792363-b0e1-4b67-992b-2851234d5ea6";
    private static String accountID = "c340667e266e177d01266f823c130145";
    private static String sessionParams = "{\"tzdiff\":\"-07:00\",\"timezoneid\":\"23\",\"roleid\":\"ff8080812b99b47f012b99ba5e8d0003\"," +
            "\"callwith\":1,\"companyPreferences\":\"\",\"dateformatid\":\"1\",\"lid\":\"99f1eb77-cac9-41bb-977b-c8bc17fb3daa\",\"companyid\":\"a4792363-b0e1-4b67-992b-2851234d5ea6\"," +
            "\"username\":\"demo\",\"currencyid\":\"1\",\"company\":\"Demo\",\"success\":true,\"superuser\":\"ff8080812b99b47f012b99ba5e8d0003\",\"timeformat\":2,\"userfullname\":\"Deskera \"," +
            "\"perms\":[{\"creditterm\":3},{\"paymentmethod\":3},{\"customer\":255},{\"vendor\":255},{\"coa\":4095},{\"invoice\":4294967295},{\"fstatement\":262143},{\"audittrail\":1},{\"useradmin\":7},{\"accpref\":3},{\"groups\":3},{\"product\":4095},{\"uom\":3},{\"bankreconciliation\":1},{\"currencyexchange\":3},{\"salesbyitem\":1},{\"vendorinvoice\":4294967295},{\"qanalysis\":3},{\"fixedasset\":1023},{\"masterconfig\":15},{\"importlog\":1},{\"tax\":3}]}";


    public accCustomerControllerTest(String testName) {
        super(testName);
    }

    public MockHttpSession createUserMockSession(JSONObject jObj) throws ServiceException {
        MockHttpSession session = new MockHttpSession();
        try {
            session.setAttribute("username", jObj.getString("username"));
        	session.setAttribute("userid", jObj.getString("lid"));
        	session.setAttribute("companyid", jObj.getString("companyid"));
        	session.setAttribute("company", jObj.getString("company"));
        	session.setAttribute("timezoneid", jObj.getString("timezoneid"));
        	session.setAttribute("tzdiff", jObj.getString("tzdiff"));
        	session.setAttribute("dateformatid", jObj.getString("dateformatid"));
        	session.setAttribute("currencyid", jObj.getString("currencyid"));
        	session.setAttribute("callwith", jObj.getString("callwith"));
            session.setAttribute("timeformat", jObj.getString("timeformat"));
            session.setAttribute("companyPreferences", jObj.getString("companyPreferences"));
            session.setAttribute("roleid", jObj.getString("roleid"));
        	session.setAttribute("initialized", "true");
        	session.setAttribute("userfullname", jObj.getString("userfullname"));
			JSONArray jarr = jObj.getJSONArray("perms");
        	for (int l = 0; l < jarr.length(); l++) {
				String keyName = jarr.getJSONObject(l).names().get(0)
						.toString();
				session.setAttribute(keyName, jarr.getJSONObject(l)
						.get(keyName));
			}
            return session;
        } catch (JSONException e) {
            throw ServiceException.FAILURE("sessionHandlerImpl.createUserMockSession", e);
        }
    }

    /*
     *** spring version 2.5.6 and above when extending from AbstractJUnit38SpringContextTests
     *
     *
    protected AbstractXmlApplicationContext createApplicationContext() {
    return new ClassPathXmlApplicationContext("test-applicationContext.xml");
    }*/
    protected String[] getConfigLocations() {
        return new String[]{"test-applicationContext.xml"};
    }

    /**
     * Spring will automatically inject the Hibernate session factory on startup
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setaccAccountDAO(accAccountDAO accAccountDAOObj) {
        this.accAccountDAOObj = accAccountDAOObj;
    }

    public void setaccCustomerDAO(accCustomerDAO accCustomerDAOObj) {
        this.accCustomerDAOObj = accCustomerDAOObj;
    }

    public void setaccountingHandlerDAO(AccountingHandlerDAO accountingHandlerDAOObj) {
        this.accountingHandlerDAOObj = accountingHandlerDAOObj;
    }

    public void setexportMPXDAOImpl(exportMPXDAOImpl exportMPXDAOImplObj) {
        this.exportMPXDAOImplObj = exportMPXDAOImplObj;
    }

    public void setimportHandler(ImportHandler importHandlerObj) {
        this.importHandlerObj = importHandlerObj;
    }

    public void setauditTrailDAO(auditTrailDAO auditTrailDAOObj) {
        this.auditTrailDAOObj = auditTrailDAOObj;
    }

//    /**
//     * Test of manageCustomer method, of class accCustomerController.
//     */
//    public void testManageCustomer() {
//        System.out.println("manageCustomer");
//        HttpServletRequest request = null;
//        HttpServletResponse response = null;
//        accCustomerController instance = new accCustomerController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.manageCustomer(request, response);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getCustomers method, of class accCustomerController.
     */
    public void testGetCustomers() {
        System.out.println("getCustomers");
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
            request.setSession(session);
            request.setParameter("deleted","false");
            String[] groups = new String[]{"10"};
            request.setParameter("group",groups);
            request.setParameter("limit","15");
            request.setParameter("mode","2");
            request.setParameter("nondeleted","false");
            request.setParameter("start","0");

            MockHttpServletResponse response = new MockHttpServletResponse();
            accCustomerController instance = new accCustomerController();
            HashMap<String, Object> requestParams = instance.getCustomerRequestMap(request);

            instance.setaccAccountDAO(accAccountDAOObj);
            instance.setaccCustomerDAO(accCustomerDAOObj);
            instance.setSuccessView("jsonView");
            KwlReturnObject result = accCustomerDAOObj.getCustomer(requestParams);
            //To Check success flag in KWLReturnObject
            assertTrue("Success Flag false : ", result.isSuccessFlag());
            //To Check if fetched list is not null
            assertNotNull("Result Entrylist is NULL : ", result.getEntityList());

//            ModelAndView result = instance.getCustomers(request, response);
//            JSONObject job = new JSONObject(result.getModel().get("model").toString());
//            assertTrue(job.getBoolean("success"));

            //Quicksearch
            request.setParameter("ss","test");
            requestParams = instance.getCustomerRequestMap(request);
            result = accCustomerDAOObj.getCustomer(requestParams);
            //To Check success flag in KWLReturnObject
            assertTrue("Success Flag false : ", result.isSuccessFlag());
            //To Check if fetched list is not null
            assertNotNull("Result Entrylist is NULL : ", result.getEntityList());

//            result = instance.getCustomers(request, response);
//            job = new JSONObject(result.getModel().get("model").toString());
//            assertTrue(job.getBoolean("success"));
        } catch (ServiceException ex) {
            throw new AssertException("Service Exception - error creating user session");
        } catch (JSONException ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        } catch (Exception ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        }
    }

    /**
     * Test of saveCustomer method, of class accCustomerController.
     */
    public void testSaveCustomer_add() {
        System.out.println("saveCustomer");
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
            request.setParameter("accid", "");
            request.setParameter("accname", "Test-Data");
            request.setParameter("address", "Test Address1");
            request.setParameter("bankaccountno", "32434234");
            request.setParameter("category", "");
            request.setParameter("contactno", "");
            request.setParameter("contactno2", "");
            request.setParameter("copyadress", "on");
            request.setParameter("creationDate", "Oct 20, 2010 12:00:00 AM");
            request.setParameter("currencyid", "1");
            request.setParameter("debitType", "false");
            request.setParameter("email", "sagar.ahire@mailinator.com");
            request.setParameter("fax", "");
            request.setParameter("issub", "on");
            request.setParameter("mode", "1");
            request.setParameter("openbalance", "10");
            request.setParameter("other", "Test Information");
            request.setParameter("parentid", "c340667e277b61e001277b91b553005d");
            request.setParameter("shippingaddress", "Test Address1");
            request.setParameter("taxeligible", "false");
            request.setParameter("taxidnumber", "");
            request.setParameter("taxno", "Test TaxID");
            request.setParameter("termid", "c340667e26bbb6290126bbc4a7a90001");
            request.setParameter("title", "Mr.");

            request.setSession(session);
            MockHttpServletResponse response = new MockHttpServletResponse();
            accCustomerController instance = new accCustomerController();
            instance.setaccAccountDAO(accAccountDAOObj);
            instance.setaccCustomerDAO(accCustomerDAOObj);
            instance.setauditTrailDAO(auditTrailDAOObj);
            instance.setSuccessView("jsonView");
            instance.setTxnManager((HibernateTransactionManager) transactionManager);
            ModelAndView result = instance.saveCustomer(request, response);
            JSONObject job = new JSONObject(result.getModel().get("model").toString());
            //commit the transaction to verify result in database.
//            transactionManager.commit(transactionStatus);
            assertTrue(job.getBoolean("success"));
        } catch (ServiceException ex) {
            throw new AssertException("Service Exception - error creating user session");
        } catch (JSONException ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        }
    }

    public void testSaveCustomer_edit() {
        System.out.println("saveCustomer");
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
            request.setParameter("accid", accountID);
            request.setParameter("accname", "Test-Data1");
            request.setParameter("address", "Test Address1");
            request.setParameter("bankaccountno", "32434234");
            request.setParameter("category", "");
            request.setParameter("contactno", "");
            request.setParameter("contactno2", "");
            request.setParameter("copyadress", "on");
            request.setParameter("creationDate", "Oct 20, 2010 12:00:00 AM");
            request.setParameter("currencyid", "1");
            request.setParameter("debitType", "false");
            request.setParameter("email", "sagar.ahire@mailinator.com");
            request.setParameter("fax", "");
            request.setParameter("issub", "on");
            request.setParameter("mode", "1");
            request.setParameter("openbalance", "10");
            request.setParameter("other", "Test Information");
            request.setParameter("parentid", "c340667e277b61e001277b91b553005d");
            request.setParameter("shippingaddress", "Test Address1");
            request.setParameter("taxeligible", "false");
            request.setParameter("taxidnumber", "");
            request.setParameter("taxno", "Test TaxID");
            request.setParameter("termid", "c340667e26bbb6290126bbc4a7a90001");
            request.setParameter("title", "Mr.");

            request.setSession(session);
            MockHttpServletResponse response = new MockHttpServletResponse();
            accCustomerController instance = new accCustomerController();
            instance.setaccAccountDAO(accAccountDAOObj);
            instance.setaccCustomerDAO(accCustomerDAOObj);
            instance.setauditTrailDAO(auditTrailDAOObj);
            instance.setSuccessView("jsonView");
            instance.setTxnManager((HibernateTransactionManager) transactionManager);
            ModelAndView result = instance.saveCustomer(request, response);
            JSONObject job = new JSONObject(result.getModel().get("model").toString());
            //commit the transaction to verify result in database.
//            transactionManager.commit(transactionStatus);
            assertTrue(job.getBoolean("success"));
        } catch (ServiceException ex) {
            throw new AssertException("Service Exception - error creating user session");
        } catch (JSONException ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        }
    }

    /**
     * Test of getAddress method, of class accCustomerController.
     */
    public void testGetAddress() {
        System.out.println("getAddress");
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
            request.setSession(session);
            request.setParameter("customerid",accountID);

            MockHttpServletResponse response = new MockHttpServletResponse();
            accCustomerController instance = new accCustomerController();
            instance.setaccountingHandlerDAO(accountingHandlerDAOObj);
            instance.setSuccessView("jsonView");
            ModelAndView result = instance.getAddress(request, response);
            JSONObject job = new JSONObject(result.getModel().get("model").toString());
            assertTrue(job.getBoolean("success"));
        } catch (ServiceException ex) {
            throw new AssertException("Service Exception - error creating user session");
        } catch (JSONException ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        }
    }

//    /**
//     * Test of exportCustomer method, of class accCustomerController. Need to uncomment and write test case for this.
//     */
//    public void testExportCustomer_csv() {
//        System.out.println("exportCustomer");
//        try {
//            MockHttpServletRequest request = new MockHttpServletRequest();
//            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
//            request.setSession(session);
//            request.setParameter("accountid","");
//            request.setParameter("align","none,none,none,none,currency,date,none,none,none,none,none");
//            request.setParameter("config","undefined");
//            request.setParameter("deleted","false");
//            request.setParameter("enddate","");
//            request.setParameter("filename","Accounts Receiva...");
//            request.setParameter("filetype","csv");
//            request.setParameter("get","113");
//            request.setParameter("group","10");
//            request.setParameter("header","accname,address,email,contactno,openbalance,creationDate,openbalance,currencyname,other,shippingaddress,termname");
//            request.setParameter("mode","2");
//            request.setParameter("name","undefined");
//            request.setParameter("nondeleted","false");
//            request.setParameter("stdate","");
//            request.setParameter("title","Name,Address,Email,Contact No,Opening Balance (In Home Currency),Creation Date,Opening Balance Type,Currency,Other Information,Default Address,Credit Term");
//            request.setParameter("width","75,75,110,75,75,150,75,75,50,75,125");
//
//            MockHttpServletResponse response = new MockHttpServletResponse();
//            accCustomerController instance = new accCustomerController();
//            instance.setaccAccountDAO(accAccountDAOObj);
//            instance.setaccCustomerDAO(accCustomerDAOObj);
//            instance.setexportMPXDAOImpl(exportMPXDAOImplObj);
//            instance.setSuccessView("jsonView");
//            ModelAndView result = instance.exportCustomer(request, response);
//            JSONObject job = new JSONObject(result.getModel().get("model").toString());
//            assertTrue(job.getBoolean("success"));
//        } catch (ServiceException ex) {
//            throw new AssertException("Service Exception - error creating user session");
//        } catch (JSONException ex) {
//            throw new AssertException("JSON Exception - error parsing model json");
//        }
//    }
//
//    public void testExportCustomer_pdf() {
//        System.out.println("exportCustomer");
//        try {
//            MockHttpServletRequest request = new MockHttpServletRequest();
//            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
//            request.setSession(session);
//            String config =	"{\"landscape\":\"true\",\"pageBorder\":\"true\",\"gridBorder\":\"true\",\"title\":\"\",\"subtitles\":\"\",\"headNote\":\"\",\"showLogo\":\"false\",\"headDate\":\"false\",\"footDate\":\"false\",\"footPager\":\"false\",\"headPager\":\"false\",\"footNote\":\"\",\"textColor\":\"000000\",\"bgColor\":\"FFFFFF\"}";
//            String gridconfig =	"{ data:[{'header':'accname','title':'Name','width':'75','align':''},{'header':'address','title':'Address','width':'75','align':''},{'header':'email','title':'Email','width':'110','align':''},{'header':'contactno','title':'Contact No','width':'75','align':''},{'header':'openbalance','title':'Opening Balance (In Home Currency)','width':'75','align':'currency'},{'header':'creationDate','title':'Creation Date','width':'150','align':'date'},{'header':'openbalance','title':'Opening Balance Type','width':'75','align':''},{'header':'currencyname','title':'Currency','width':'75','align':''},{'header':'other','title':'Other Information','width':'50','align':''},{'header':'shippingaddress','title':'Default Address','width':'75','align':''},{'header':'termname','title':'Credit Term','width':'125','align':''}]}";
//
//            request.setParameter("accountid","");
//            request.setParameter("config",config);
//            request.setParameter("gridconfig",gridconfig);
//            request.setParameter("enddate","");
//            request.setParameter("filename","Accounts Receiva...");
//            request.setParameter("filetype","pdf");
//            request.setParameter("get","113");
//            request.setParameter("group","10");
//            request.setParameter("mode","2");
//            request.setParameter("stdate","");
//
//            MockHttpServletResponse response = new MockHttpServletResponse();
//            accCustomerController instance = new accCustomerController();
//            instance.setaccAccountDAO(accAccountDAOObj);
//            instance.setaccCustomerDAO(accCustomerDAOObj);
//            instance.setexportMPXDAOImpl(exportMPXDAOImplObj);
//            instance.setSuccessView("jsonView");
//            ModelAndView result = instance.exportCustomer(request, response);
//            JSONObject job = new JSONObject(result.getModel().get("model").toString());
//            assertTrue(job.getBoolean("success"));
//        } catch (ServiceException ex) {
//            throw new AssertException("Service Exception - error creating user session");
//        } catch (JSONException ex) {
//            throw new AssertException("JSON Exception - error parsing model json");
//        }
//    }

//    /**
//     * Test of importCustomer method, of class accCustomerController. Need to uncomment and write test case for this.
//     */
//    public void testImportCustomer() {
//        System.out.println("importCustomer");
//        HttpServletRequest request = null;
//        HttpServletResponse response = null;
//        accCustomerController instance = new accCustomerController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.importCustomer(request, response);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getCustomersByCategory method, of class accCustomerController.
     */
    public void testGetCustomersByCategory() {
        System.out.println("getCustomersByCategory");
        try {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpSession session = createUserMockSession(new JSONObject(sessionParams));
            request.setSession(session);
            request.setParameter("start","0");
            request.setParameter("limit","15");

            MockHttpServletResponse response = new MockHttpServletResponse();
            accCustomerController instance = new accCustomerController();
            instance.setaccCustomerDAO(accCustomerDAOObj);
            instance.setSuccessView("jsonView");
            ModelAndView result = instance.getCustomersByCategory(request, response);
            JSONObject job = new JSONObject(result.getModel().get("model").toString());
            assertTrue(job.getBoolean("success"));
        } catch (ServiceException ex) {
            throw new AssertException("Service Exception - error creating user session");
        } catch (JSONException ex) {
            throw new AssertException("JSON Exception - error parsing model json");
        }
    }
}
