let main = {

    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.saveMember();
        });

        $('#btn-login').on('click', function () {
            _this.memberLogin();
        });

        $('#btn-modify').on('click', function () {
            _this.modifyMember();
        });
    },

    saveMember: function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let data = {
            username: $('#username').val(),
            password: $('#password').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            phoneNumber: $('#phoneNumber').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/members',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            cache: false
        }).done(function () {
            alert('회원가입이 완료되었습니다.');
            window.location.assign('/home');
        }).fail(function (response) {
            markingErrorField(response);
        });
    },

    memberLogin: function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let data = {
            username: $('#username').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            cache: false
        }).done(function () {
            window.location.assign('/home');
        }).fail(function (response) {
            markingErrorField(response);
        });
    },

    modifyMember : function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let memberId = $('#id').val();
        let data = {
            password: $('#password').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            phoneNumber: $('#phoneNumber').val()
        };

        if (data.password === '' || data.password == null) {
            data.password = 'DEFAULT__PASSWORD';
        }

        $.ajax({
            type: 'PUT',
            url: '/api/v1/members/' + memberId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            },
            cache: false
        }).done(function () {
            alert('회원정보가 정상적으로 수정되었습니다.');
            window.location.assign('/members/my-info');
        }).fail(function (response) {
            markingErrorField(response);
        });
    }
};

main.init();