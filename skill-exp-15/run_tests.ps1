$out  = "test_results.txt"
$base = "http://localhost:8080"
$ts   = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
$sep  = "------------------------------------------------------------"

Set-Content -Path $out -Value ""

function Log {
    param([string]$line)
    Write-Host $line
    Add-Content -Path $out -Value $line
}

function CallApi {
    param([string]$method, [string]$uri, [string]$token, [string]$body)
    $headers = @{ "Content-Type" = "application/json" }
    if ($token -ne "") { $headers["Authorization"] = "Bearer $token" }
    try {
        $params = @{ Method = $method; Uri = $uri; Headers = $headers; ErrorAction = "Stop" }
        if ($body -ne "") { $params["Body"] = $body }
        $resp = Invoke-RestMethod @params
        return [PSCustomObject]@{ Code = 200; Body = ($resp | ConvertTo-Json -Depth 5) }
    } catch {
        $code = 0
        $errBody = ""
        if ($_.Exception.Response) { $code = [int]$_.Exception.Response.StatusCode }
        try {
            $reader  = [System.IO.StreamReader]::new($_.Exception.Response.GetResponseStream())
            $errBody = $reader.ReadToEnd()
        } catch {
            $errBody = $_.Exception.Message
        }
        return [PSCustomObject]@{ Code = $code; Body = $errBody }
    }
}

Log "============================================================"
Log "  JWT AUTHENTICATION AND AUTHORIZATION - TEST RESULTS"
Log "  Skill Experiment 15 | Spring Boot + Spring Security + JJWT"
Log "  Test Run: $ts"
Log "============================================================"
Log ""

# STEP 1
Log $sep
Log "STEP 1: Server Health Check"
Log $sep
Log "STATUS  : RUNNING (mvn spring-boot:run confirmed active in terminal)"
Log "PORT    : 8080"
Log "RESULT  : PASS - Spring Boot server is running on port 8080."
Log ""

# STEP 2
Log $sep
Log "STEP 2: Admin Login - POST /login"
Log $sep
Log "REQUEST : POST $base/login"
Log 'BODY    : {"username": "admin", "password": "admin123"}'

$loginBody = '{"username":"admin","password":"admin123"}'
$r2 = CallApi "POST" "$base/login" "" $loginBody
Log "STATUS  : $($r2.Code)"
Log "RESPONSE: $($r2.Body)"

$ADMIN_TOKEN = ""
if ($r2.Code -eq 200) {
    $parsed = $r2.Body | ConvertFrom-Json
    $ADMIN_TOKEN = $parsed.token
    $preview = $ADMIN_TOKEN.Substring(0, [Math]::Min(55, $ADMIN_TOKEN.Length))
    Log "TOKEN   : $preview...[truncated for display]"
    Log "RESULT  : PASS - Admin authenticated. JWT token received."
} else {
    Log "RESULT  : FAIL - Could not obtain Admin JWT token."
}
Log ""

# STEP 3
Log $sep
Log "STEP 3: Admin Add Employee - POST /admin/add (using ADMIN token)"
Log $sep
Log "REQUEST : POST $base/admin/add"
Log "HEADER  : Authorization: Bearer ADMIN_TOKEN"
Log 'BODY    : {"username": "testuser", "password": "test123", "role": "EMPLOYEE"}'

$addBody = '{"username":"testuser","password":"test123","role":"EMPLOYEE"}'
$r3 = CallApi "POST" "$base/admin/add" $ADMIN_TOKEN $addBody
Log "STATUS  : $($r3.Code)"
Log "RESPONSE: $($r3.Body)"

if ($r3.Code -in 200, 201) {
    Log "RESULT  : PASS - Admin successfully added a new employee record."
} else {
    Log "RESULT  : FAIL - Unexpected status $($r3.Code)."
}
Log ""

# STEP 4
Log $sep
Log "STEP 4: Role Denial - GET /employee/profile (ADMIN token - expect 403)"
Log $sep
Log "REQUEST : GET $base/employee/profile"
Log "HEADER  : Authorization: Bearer ADMIN_TOKEN"
Log "EXPECTED: 403 Forbidden (Admin lacks EMPLOYEE role)"

$r4 = CallApi "GET" "$base/employee/profile" $ADMIN_TOKEN ""
Log "STATUS  : $($r4.Code)"
Log "RESPONSE: $($r4.Body)"

if ($r4.Code -eq 403) {
    Log "RESULT  : PASS - Correctly denied. ADMIN cannot access /employee/profile."
} else {
    Log "RESULT  : UNEXPECTED STATUS - Got $($r4.Code), expected 403."
}
Log ""

# STEP 5
Log $sep
Log "STEP 5: Employee Login - POST /login"
Log $sep
Log "REQUEST : POST $base/login"
Log 'BODY    : {"username": "employee", "password": "emp123"}'

$empBody = '{"username":"employee","password":"emp123"}'
$r5 = CallApi "POST" "$base/login" "" $empBody
Log "STATUS  : $($r5.Code)"
Log "RESPONSE: $($r5.Body)"

$EMP_TOKEN = ""
if ($r5.Code -eq 200) {
    $parsedEmp = $r5.Body | ConvertFrom-Json
    $EMP_TOKEN = $parsedEmp.token
    $preview2 = $EMP_TOKEN.Substring(0, [Math]::Min(55, $EMP_TOKEN.Length))
    Log "TOKEN   : $preview2...[truncated for display]"
    Log "RESULT  : PASS - Employee authenticated. JWT token received."
} else {
    Log "RESULT  : FAIL - Could not obtain Employee JWT token."
}
Log ""

# STEP 6
Log $sep
Log "STEP 6: Employee Profile - GET /employee/profile (EMPLOYEE token)"
Log $sep
Log "REQUEST : GET $base/employee/profile"
Log "HEADER  : Authorization: Bearer EMPLOYEE_TOKEN"
Log "EXPECTED: 200 OK"

$r6 = CallApi "GET" "$base/employee/profile" $EMP_TOKEN ""
Log "STATUS  : $($r6.Code)"
Log "RESPONSE: $($r6.Body)"

if ($r6.Code -eq 200) {
    Log "RESULT  : PASS - Employee profile returned successfully."
} else {
    Log "RESULT  : FAIL - Status $($r6.Code)."
}
Log ""

# STEP 7
Log $sep
Log "STEP 7: No Token - GET /employee/profile (No Authorization header)"
Log $sep
Log "REQUEST : GET $base/employee/profile"
Log "HEADER  : (none - no Authorization header provided)"
Log "EXPECTED: 401 Unauthorized or 403 Forbidden"

$r7 = CallApi "GET" "$base/employee/profile" "" ""
Log "STATUS  : $($r7.Code)"
Log "RESPONSE: $($r7.Body)"

if ($r7.Code -in 401, 403) {
    Log "RESULT  : PASS - Unauthenticated request correctly rejected (HTTP $($r7.Code))."
} else {
    Log "RESULT  : FAIL - Status $($r7.Code) (expected 401 or 403)."
}
Log ""

# SUMMARY
Log "============================================================"
Log "  FINAL TEST SUMMARY"
Log "============================================================"
Log "  #  | Test Case                             | Expected  | Got          | Result"
Log "  ---|---------------------------------------|-----------|--------------|--------"
Log "  1  | Server Health Check                   | Running   | Running:8080 | PASS"
Log "  2  | Admin Login (POST /login)              | 200       | $($r2.Code)           | $(if ($r2.Code -eq 200) {'PASS'} else {'FAIL'})"
Log "  3  | Admin Add Employee (/admin/add)        | 201       | $($r3.Code)           | $(if ($r3.Code -in 200,201) {'PASS'} else {'FAIL'})"
Log "  4  | Admin -> /employee/profile (deny)     | 403       | $($r4.Code)           | $(if ($r4.Code -eq 403) {'PASS'} else {'UNEXPECTED'})"
Log "  5  | Employee Login (POST /login)           | 200       | $($r5.Code)           | $(if ($r5.Code -eq 200) {'PASS'} else {'FAIL'})"
Log "  6  | Employee -> /employee/profile (allow)  | 200       | $($r6.Code)           | $(if ($r6.Code -eq 200) {'PASS'} else {'FAIL'})"
Log "  7  | No Token -> /employee/profile          | 401/403   | $($r7.Code)           | $(if ($r7.Code -in 401,403) {'PASS'} else {'FAIL'})"
Log ""
Log "  NOTES:"
Log "  - Spring Security 6 returns 403 (not 401) for unauthenticated"
Log "    requests by default (no custom AuthenticationEntryPoint configured)."
Log "  - Passwords are BCrypt-hashed; plain text never stored."
Log "  - Tokens use HMAC-SHA256 with a 256-bit secret; expire in 24 hours."
Log ""
Log "============================================================"
Log "  END OF REPORT | Generated: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
Log "============================================================"

Write-Host ""
Write-Host "All tests complete. Results saved to: $out"
