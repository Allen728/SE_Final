<script type="text/javascript">
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $('#highlight').toggleClass('active');
            $('.fas').removeClass();
            $(this).toggleClass('active');
    });
    
    $('#register').click(function(){
        var name = $('#member_name').val();
        var gender = $('#member_gender').val();
        var bday = $('#member_bday').val();
        var phone = $('#member_phone').val();
        var email = $('#member_email').val();
        var password = $('#member_psw').val();
        

        var email_rule = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
        var password_rule = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

        if(name == "" || gender == "" || bday == "" || phone == "" || email == "" || password == "" ) {
            alert("尚有必填欄位未填完！");
        }
        else if (!email_rule.test(email)) {
            alert("Email格式不符！");
        }
        else if(!password_rule.test(password)) {
            alert("密碼格式不符，長度至少8，且至少包含一個數字和英文字母！");
        }
        else {
            // 將資料組成JSON格式
            var data_object = {
                "name": name,
                "gender": gender,
                "bday": bday,
                "phone": phone,
                "email": email,
                "password": password
            };

            // 將JSON格式轉換成字串
            var data_string = JSON.stringify(data_object);

            // 發出POST的AJAX請求
            $.ajax({
                    type: "POST",
                    url: "api/user.do",
                    data: data_string,
                    crossDomain: true,
                    cache: false,
                    dataType: 'json',
                    timeout: 5000,
                    success: function (response) {
                     if(response.status == 200){
                      alert(response.message);
                      location.href='before_user_login.html';
                     }
                     else if(response.status == 400){
                      alert(response.message);
                     }
                    },
                    error: function () {
                        alert("無法連線到伺服器！");
                    }
            });
        }
    })
});
</script>