function inputData(){
    var data = $('#form').serialize();
    $.ajax({
        url: "post1/{no}",
        data: data,
        type:"GET",
        cache: false
    }).done(function (fragment) {
        $("#list").replaceWith(fragment);
    });

}

