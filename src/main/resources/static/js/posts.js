let main = {

    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.saveBoard();
        });

        $('#btn-update').on('click', function () {
            _this.updateBoard();
        });

        $('#btn-search').on('click', function () {
            _this.searchBoard();
        });
    },

    saveBoard: function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache: false,
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            alert('글이 정상적으로 등록되었습니다.');
            const resData = response.data;
            window.location.assign('/posts/' + resData.author + '/' + resData.postId);
        }).fail(function (response) {
            markingErrorField(response);
        });
    },

    updateBoard: function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let postId = $('#postId').val();
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        console.log(data);

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + postId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            cache: false,
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            const resData = response.data;
            alert('글이 정상적으로 수정되었습니다.');
            window.location.assign('/posts/' + resData.author + '/' + resData.postId);
        }).fail(function (response) {
            markingErrorField(response);
        });
    },

    searchBoard: function () {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        let dateData = {
            startDate: $("#datetimepicker1").find("input").val(),
            endDate: $("#datetimepicker2").find("input").val()
        };

        console.log(dateData);

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts/search',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(dateData),
            cache: false,
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            alert('선택된 데이터 = ' + response);
        }).fail(function (response) {
            markingErrorField(response);
        });
    }
};


function sendFile(file, el) {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    let form_data = new FormData();
    form_data.append('file', file);
    $.ajax({
        type: 'POST',
        url: '/images',
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        beforeSend : function(xhr)
        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        },
        processData: false,
        data: form_data,
        success: function (url) {
            $(el).summernote('insertImage', url, function ($image) {
                $image.css('width', '25%');
            });
        }
    });
}

function deleteFile(src) {
    let index = src.lastIndexOf('/');
    let imgId = src.substr(index + 1);
    console.log('imgId=' + imgId);

    $.ajax({
        type: 'DELETE',
        url: '/images/' + imgId,
        cache: false,
        async: false,
        success: function () {
            console.log('이미지가 정상적으로 삭제되었습니다.');
        }
    });
}

main.init();