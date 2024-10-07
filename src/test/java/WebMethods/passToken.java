package WebMethods;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Listeners(iInvokedListener.class)
public class passToken {

@BeforeClass (alwaysRun = true)
public  Properties loadproperties() throws IOException {
    Properties properties = new Properties();

    FileInputStream input = new FileInputStream("src/test/credentials.properties");
    properties.load(input);

    return properties;
}


public Response getAccessToken() throws IOException {

        baseURI = "https://apigwis.satrans.com.sa";
        useRelaxedHTTPSValidation();



        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type","client_credentials");

        Properties props = loadproperties();

        String Username =props.getProperty("Username");
        String Password =props.getProperty("Password");

        System.out.println(Username);
        System.out.println(Password);

        Response response = given().auth().preemptive()
                .basic(Username , Password)
                .contentType("application/x-www-form-urlencoded")
                .formParams(credentials)
                .when()
                .post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        String  accessToken = response.jsonPath().getString("access_token");

        System.out.println(accessToken);

        Map <String ,String> request_output = new HashMap<>();
        String status_code = Integer.toString(response.getStatusCode());

        request_output.put("status_code" , status_code);
        request_output.put("response_body" , accessToken );

        return response ;
    }



    @Test
    public void passBearerToken () throws IOException {

        Properties properties = loadproperties();
        baseURI= properties.getProperty("getTokenBaseURI");

        useRelaxedHTTPSValidation();
        Response get_token_response = getAccessToken();

        get_token_response.then().statusCode(200);
        get_token_response.then().body("",hasKey("access_token"));




        String  get_access_token_output = get_token_response.jsonPath().getString("access_token");



        System.out.println(get_access_token_output);

        Map<String,String> params = new HashMap<>();
        params.put("email","TestHR@PTCO.COM.SA");
        params.put("password","cz644x5P");
        params.put("company","PTC");

       // String access_token = get_access_token_output.

        Response Auth_User_Response = given().header("Authorization","Bearer " +get_access_token_output)
                .contentType("application/x-www-form-urlencoded")
                .queryParams(params)
                .when()
                .get("/gateway/Portal-ActiveDirectory/1.0/AuthUser");

        Auth_User_Response.then().statusCode(200);

        Auth_User_Response.then().assertThat().body("body.error_document",hasKey("error_code"));

        Assert.assertTrue(Auth_User_Response.getHeaders().hasHeaderWithName("Content-Encoding"));


//                response.getHeaders():  todo >> Retrieves all headers.
//                response.getHeader("Header-Name"):  todo >>Retrieves the value of a specific header.
//                response.getHeaders().hasHeaderWithName("Header-Name"): todo >> Checks if the specified header exists.





    }


    @Test (groups = {"permissions"})
    public void Get_Permission_Details() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("permission_name","PER/00001");
        parameters.put("company_id","1");

        Response Get_Permission_Details = GET_API(properties.getProperty("resource_Get_Permission_Details"),access_token,parameters);

        Get_Permission_Details.then().statusCode(200).log().body();
        Get_Permission_Details.then().body("get_permission_details_output.error_document",hasKey("error_code"));
        Get_Permission_Details.then().body("get_permission_details_output.message",hasKey("permission_type"));




    }

    @Test (groups = {"permissions"})
    public void Get_List_Employee_Permission() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("employee","11030");
        parameters.put("page_number","1");
        parameters.put("company_id","1");

        Response Get_List_Employee_Permission = GET_API(properties.getProperty("resource_Get_List_Employee_Permission"),access_token,parameters);

        Get_List_Employee_Permission.then().statusCode(200).log().body();
        Get_List_Employee_Permission.then().body("get_list_employee_permission_output.error_document",hasKey("error_code"));
        Get_List_Employee_Permission.then().body("get_list_employee_permission_output.data[0]",hasKey("permission_type"));




    }

    @Test(groups = {"permissions"})
    public void Create_Permission () throws IOException {

        Properties properties= loadproperties();
        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");


        HashMap<String,String> parameters = new HashMap<>();
        // parameters.put("company_id","1");

        Response Create_Permission= POST_API(properties.getProperty("resource_Create_Permission"),access_token, properties.getProperty("body_create_permission"), parameters);



        Create_Permission.then().statusCode(200).log().body();
        Create_Permission.then().body("create_permission.error_document",hasKey("error_code"));



    }

    @Test (groups = {"permissions"})
    public void Get_List_Employee_Manager_Permission() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("employee_manager","11030");
        parameters.put("page_no","1");
        parameters.put("page_length","10");
        parameters.put("company_id","1");

        Response Get_List_Employee_Manager_Permission = GET_API(properties.getProperty("resource_Get_List_Employee_Manager_Permission"),access_token,parameters);

        Get_List_Employee_Manager_Permission.then().statusCode(200).log().body();
        Get_List_Employee_Manager_Permission.then().body("get_list_employee_manager_permission_output.error_document",hasKey("error_code"));
        Get_List_Employee_Manager_Permission.then().body("get_list_employee_manager_permission_output.data[0]",hasKey("permission_type"));




    }

    @Test (groups = {"permissions"})
    public void Accept_Reject_Permission() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("permission_name","PER/00001");
        parameters.put("workflow_state","Rejected by Line Manager");
        parameters.put("manager","11030");
        parameters.put("company_id","1");

        Response Accept_Reject_Permission = GET_API(properties.getProperty("resource_Accept_Reject_Permission"),access_token,parameters);

        Accept_Reject_Permission.then().statusCode(200).log().body();
        Accept_Reject_Permission.then().body("accept_reject_permission_output.error_document",hasKey("error_code"));
        Accept_Reject_Permission.then().body("accept_reject_permission_output.message",hasKey("permission_type"));




    }

    @Test (groups = {"permissions"})
    public void Get_Monthly_Balance_For_Permissions() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("employee_id","11686");
        parameters.put("month","07-2023");
        parameters.put("company_id","1");

        Response Get_Monthly_Balance_For_Permissions = GET_API(properties.getProperty("resource_Get_Monthly_Balance_For_Permissions"),access_token,parameters);

        Get_Monthly_Balance_For_Permissions.then().statusCode(200).log().body();
        Get_Monthly_Balance_For_Permissions.then().body("get_monthly_balance_for_permissions_output.error_document",hasKey("error_code"));
        Get_Monthly_Balance_For_Permissions.then().body("get_monthly_balance_for_permissions_output.message",hasKey("monthly_balance"));




    }

    @Test (groups = {"ERP"})
    public void Get_Employee_Info() throws IOException{
        Properties properties= loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("employee_id","10637");
        parameters.put("company_id","1");

        Response get_employee_info_response = GET_API(properties.getProperty("resource_ERP_GetEmployeeInfo"),access_token,parameters);

        get_employee_info_response.then().statusCode(200).log().body().
                body("employee_info.error_document",hasKey("error_code"));

    }

    @Test (groups = {"ERP"})
    public void Get_BFL() throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("employee_id","11030");
        parameters.put("from_date","2022-01-01");
        parameters.put("to_date","2024-12-31");
        parameters.put("company_id", "1");

        Response get_BFL = GET_API(properties.getProperty("resource_ERP_GetEmployeeInfo"), access_token, parameters);

        get_BFL.then().statusCode(200);
        get_BFL.then().assertThat().body("employee_info.error_document",hasKey("error_code"));
    }


    @Test(groups = {"ERP"})
    public void update_BFL_status() throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("employee_id","11030");
        parameters.put("app_name","13262-HR-LAP-2024-05472-66659");
        parameters.put("company_id", "2");

        Response update_BFL_status = GET_API(properties.getProperty("resource_ERP_GetEmployeeInfo"), access_token, parameters);

        update_BFL_status.then().statusCode(200);
        update_BFL_status.then().assertThat().body("employee_info.error_document",hasKey("error_code"));
    }


    @Test(groups = {"ERP"})
    public void Get_BFL_For_Managers () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        //parameters.put("page_no","1");
        parameters.put("manager","11030");
        //parameters.put("from_date","2022-06-01");
        //parameters.put("to_date","2024-06-30");
        parameters.put("company_id","1");

        Response  Get_BFL_For_Managers = GET_API(properties.getProperty("resource_ERP_Get_BFL_managers"), access_token, parameters);

        System.out.println(Get_BFL_For_Managers.body().toString());

        Get_BFL_For_Managers.then().statusCode(200).log().body();
     //   update_BFL_status.then().assertThat().body("employee_info.error_document",hasKey("error_code"));
    }

    @Test(groups = {"ERP"})
    public void check_BFL () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("leave_name","HR-LAP-2024-00002");
        parameters.put("joining_date","24-05-2024");
        parameters.put("company_id","1");

        Response check_BFL = GET_API(properties.getProperty("resource_check_BFL"), access_token, parameters);


        check_BFL.then().statusCode(200).log().body();
        check_BFL.then().assertThat().body("checkBFL_out.error_document",hasKey("error_code"));
    }

    @Test(groups = {"ERP"})
    public void List_My_Employees_leaves () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("manager","11030");
        parameters.put("page_no","1");
        parameters.put("company_id","1");

        Response List_My_Employees_leaves = GET_API(properties.getProperty("resource_list_myEmployees_leaves"), access_token, parameters);


        List_My_Employees_leaves.then().statusCode(200).log();
        List_My_Employees_leaves.then().assertThat().body("leaves_list.error_document",hasKey("error_code"));
    }

    @Test(groups = {"ERP"})
    public void Employee_For_Manager () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("company_id","1");

        Response Employee_For_Manager = GET_API(properties.getProperty("resource_Employee_For_manager"), access_token, parameters);


        Employee_For_Manager.then().statusCode(200);
        Employee_For_Manager.then().assertThat().body("emoloyee_for_manager.error_document",hasKey("error_code"));
    }

    @Test(groups = {"ERP"})
    public void Get_Employee_Payslip () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("from_date","2024-01-01");
        parameters.put("to_date","2024-01-30");
        parameters.put("company_id","1");

        Response Get_Employee_Payslip = GET_API(properties.getProperty("resource_Get_Employee_Payslip"), access_token, parameters);


        Get_Employee_Payslip.then().statusCode(200).log().body();
        Get_Employee_Payslip.then().body("pay_slip.error_document",hasKey("error_code"));
        Get_Employee_Payslip.then().body("pay_slip.body.status_code",equalTo(202));
    }

    @Test(groups = {"ERP"})
    public void LeaveBalance () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("company_id","2");

        Response LeaveBalance = GET_API(properties.getProperty("resource_Leave_Balance"), access_token, parameters);


        LeaveBalance.then().statusCode(200).log().body();
        LeaveBalance.then().body("leave_balance.error_document",hasKey("error_code"));
        LeaveBalance.then().body("leave_balance.body",hasKey("Data"));
    }

    @Test(groups = {"ERP"})
    public void LeaveApplications () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("page_no","1");
        parameters.put("company_id","1");

        Response LeaveApplications = GET_API(properties.getProperty("resource_Leave_Applications"), access_token, parameters);


        LeaveApplications.then().statusCode(200).log().body();
        LeaveApplications.then().body("leave_applications.error_document",hasKey("error_code"));
        LeaveApplications.then().body("leave_applications.message.body[0]",hasKey("name"));
    }

    @Test(groups = {"ERP"})
    public void Employee_Salary () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","10638");
        parameters.put("company_id","2");

        Response Employee_Salary = GET_API(properties.getProperty("resource_Employee_Salary"), access_token, parameters);


        Employee_Salary.then().statusCode(200).log().body();
        Employee_Salary.then().body("employee_salary.error_document",hasKey("error_code"));
        Employee_Salary.then().body("employee_salary.message",hasKey("gross_pay"));
    }

    @Test(groups = {"ERP"})
    public void Leave_Available_Balance () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("from_date","2023-12-01");
        parameters.put("to_date","2024-07-28");
        parameters.put("leave_type","Sick Leave");
        parameters.put("company_id","1");

        Response Leave_Available_Balance = GET_API(properties.getProperty("resource_Leave_Available_Balance"), access_token, parameters);


        Leave_Available_Balance.then().statusCode(200).log().body();
        Leave_Available_Balance.then().body("leave_available_balance.error_document",hasKey("error_code"));
        Leave_Available_Balance.then().body("leave_available_balance.message",hasKey("balance"));
    }

    @Test(groups = {"ERP"})
    public void Advanced_Leave () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("company_id","1");

        Response Advanced_Leave = GET_API(properties.getProperty("resource_Advanced_Leave"), access_token, parameters);


        Advanced_Leave.then().statusCode(200).log().body();
        Advanced_Leave.then().body("advanced_leave.error_document",hasKey("error_code"));
        Advanced_Leave.then().body("advanced_leave.body[0]",hasKey("leave_type"));
    }

    @Test(groups = {"ERP"})
    public void View_Leave_Application () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("app_name","HR-LAP-2024-07529");
        parameters.put("company_id","1");

        Response View_Leave_Application = GET_API(properties.getProperty("resource_View_Leave_Application"), access_token, parameters);


        View_Leave_Application.then().statusCode(200).log().body();
        View_Leave_Application.then().body("view_leave_application.error_document",hasKey("error_code"));
        View_Leave_Application.then().body("view_leave_application.body",hasKey("leave_type"));
        View_Leave_Application.then().body("view_leave_application.body",hasKey("total_leave_days"));
    }

    @Test(groups = {"ERP"})
    public void Get_Login_SessionID () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("usr","mobile-service@ptco.com.sa");
        parameters.put("pwd","Mobile@123");
        parameters.put("company_id","1");


        Response Get_Login_SessionID = GET_API(properties.getProperty("resource_Get_Login_SessionID"), access_token, parameters);


        Get_Login_SessionID.then().statusCode(200).log().body();
        Get_Login_SessionID.then().body("login_get_session_id_output.error_document",hasKey("error_code"));
        Get_Login_SessionID.then().body("login_get_session_id_output",hasKey("session_id"));
    }

    @Test(groups = {"ERP"})
    public void Get_File_By_URL () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("session_id",properties.getProperty("session_id"));
        parameters.put("url_path","private/files/٢٠٢٤٠٥٠٨_١٠٣٥٣٨.jpg");
        parameters.put("company_id","2");


        Response Get_File_By_URL = GET_API(properties.getProperty("resource_Get_File_By_URL"), access_token, parameters);


        Get_File_By_URL.then().statusCode(200).log().body();
        Get_File_By_URL.then().body("getFile_URL_output.error_document",hasKey("error_code"));
    }

    @Test(groups = {"ERP"})
    public void Add_Leave_Application () throws IOException {

        Properties properties= loadproperties();
        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");


        HashMap<String,String> parameters = new HashMap<>();
        // parameters.put("company_id","1");

        Response post_Add_Leave_Application= POST_API(properties.getProperty("resource_Add_Leave_Application"),access_token, properties.getProperty("body_add_leave_application"), parameters);



        post_Add_Leave_Application.then().statusCode(200).log().body();
        post_Add_Leave_Application.then().body("leave_application.error_document",hasKey("error_code"));


    }

    @Test(groups = {"ERP"})
    public void Add_BFL () throws IOException {

        Properties properties= loadproperties();
        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");


        HashMap<String,String> parameters = new HashMap<>();
        // parameters.put("company_id","1");

        Response post_Add_BFL= POST_API(properties.getProperty("resource_Add_BFL"),access_token, properties.getProperty("body_add_bfl"), parameters);



        post_Add_BFL.then().statusCode(200).log().body();


    }

    @Test(groups = {"ERP"})
    public void Update_Leave () throws IOException {

        Properties properties= loadproperties();
        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");


        HashMap<String,String> parameters = new HashMap<>();
         parameters.put("company_id","1");

        Response post_Update_Leave= POST_API(properties.getProperty("resource_Update_Leave"),access_token, properties.getProperty("body_update_leave"), parameters);



        post_Update_Leave.then().statusCode(200).log().body();
        post_Update_Leave.then().body("update_leave_output.error_document",hasKey("error_code"));

    }

    @Test(groups = {"ERP"})
    public void Get_Attendance_For_Managers () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("page_no","1");
        parameters.put("company_id","1");


        Response Get_Attendance_For_Managers = GET_API(properties.getProperty("resource_Get_Attendance_For_Managers"), access_token, parameters);


        Get_Attendance_For_Managers.then().statusCode(200).log().body();
        Get_Attendance_For_Managers.then().body("get_attendance_for_manager.error_document",hasKey("error_code"));
        Get_Attendance_For_Managers.then().body("get_attendance_for_manager.message",hasKey("attendance_records"));


    }

    @Test(groups = {"ERP"})
    public void Get_Employee_Specific_Attendance () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("page_no","1");
        parameters.put("company_id","1");
        parameters.put("page_length","10");


        Response Get_Employee_Specific_Attendance = GET_API(properties.getProperty("resource_Get_Employee_Specific_Attendance"), access_token, parameters);


        Get_Employee_Specific_Attendance.then().statusCode(200).log().body();
        Get_Employee_Specific_Attendance.then().body("get_employee_specific_attendance_output.error_document",hasKey("error_code"));
        Get_Employee_Specific_Attendance.then().body("get_employee_specific_attendance_output.",hasKey("data"));


    }

    @Test(groups = {"ERP"})
    public void Get_Manager_Employee_Statistics () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee_id","11030");
        parameters.put("company_id","1");



        Response Get_Manager_Employee_Statistics = GET_API(properties.getProperty("resource_Get_Manager_Employee_Statistics"), access_token, parameters);


        Get_Manager_Employee_Statistics.then().statusCode(200).log().body();
        Get_Manager_Employee_Statistics.then().body("get_manager_employee_statistics_output.error_document",hasKey("error_code"));
        Get_Manager_Employee_Statistics.then().body("get_manager_employee_statistics_output.message",hasKey("employees_on_leave"));


    }

    @Test(groups = {"ERP"})
    public void Get_Attendance_Statistics () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("employee","11030");
        parameters.put("month_date","2024-08-15");
        parameters.put("company_id","1");



        Response Get_Manager_Employee_Statistics = GET_API(properties.getProperty("resource_Get_Attendance_Statistics"), access_token, parameters);


        Get_Manager_Employee_Statistics.then().statusCode(200).log().body();
        Get_Manager_Employee_Statistics.then().body("get_attendance_statistics_output.error_document",hasKey("error_code"));
        Get_Manager_Employee_Statistics.then().body("get_attendance_statistics_output.data[0]",hasKey("permissions"));


    }

    @Test(groups = {"ERP"})
    public void Create_Employee_Check_In () throws IOException {

        Properties properties= loadproperties();
        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");


        HashMap<String,String> parameters = new HashMap<>();
         parameters.put("company_id","1");

        Response post_Create_Employee_Check_In= POST_API(properties.getProperty("resource_Create_Employee_Check_In"),access_token, properties.getProperty("body_create_employee_check_in"), parameters);



        post_Create_Employee_Check_In.then().statusCode(200).log().body();
        post_Create_Employee_Check_In.then().body("create_employee_check_in_out_output.error_document",hasKey("error_code"));
        post_Create_Employee_Check_In.then().body("create_employee_check_in_out_output.data",hasKey("creation"));



    }

    @Test(groups = {"ERP"})
    public void Get_Department_List () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("company_id","2");



        Response Get_Department_List = GET_API(properties.getProperty("resource_Get_Department_List"), access_token, parameters);


        Get_Department_List.then().statusCode(200).log().body();
        Get_Department_List.then().body("department_list_output.error_document",hasKey("error_code"));
        Get_Department_List.then().body("department_list_output.data[0]",hasKey("department_name"));


    }

    @Test(groups = {"ERP"})
    public void Get_User_List () throws IOException {
        Properties properties = loadproperties();

        getAccessToken();
        String access_token = getAccessToken().jsonPath().getString("access_token");

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("company_id","2");



        Response Get_User_List = GET_API(properties.getProperty("resource_User_List"), access_token, parameters);


        Get_User_List.then().statusCode(200).log().body();
        Get_User_List.then().body("get_user_list_output.error_document",hasKey("error_code"));
        Get_User_List.then().body("get_user_list_output.data[0]",hasKey("employee_number"));


    }




















    public Response GET_API(String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties= loadproperties();
        baseURI =properties.getProperty("PORTAL_ERP_BASE_URL");
        Response r= given().header("Authorization","Bearer " +access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);

        return r;
    }

    public Response  POST_API (String resource_name,String access_token,String getJsonBody ,HashMap<String,String> parameters) throws IOException {

    Properties properties = loadproperties();
    baseURI=properties.getProperty("PORTAL_ERP_BASE_URL");
    Response r = given().header("Authorization","Bearer " +access_token)
            .contentType("application/json").queryParams(parameters).
            body(getJsonBody).when()
            .post(resource_name);

    return r;
    }
}


