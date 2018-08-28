$(function() {

    $.extend($.jgrid.defaults, {
        datatype: 'json',
        jsonReader : {
            repeatitems:false,
            root: "postsData",
            page: "currentPage",
            total: "totalPages",
            records: "totalRecords"
        },
/*        prmNames: {
            page: "page.page",
            rows: "page.size",
            sort: "page.sort",
            order: "page.sort.dir"
        },*/
        sortname: 'title',
        sortorder: 'asc',
        height: 'auto',
        viewrecords: true,
        rowList: [10, 20, 30],
        altRows: true,
        loadError: function(xhr, status, error) {
            alert(error);
        }
    });

    $.extend($.jgrid.edit, {
        closeAfterEdit: true,
        closeAfterAdd: true,
        ajaxEditOptions: { contentType: "application/json" },
        mtype: 'PUT',
        serializeEditData: function(data) {
            delete data.oper;
            return JSON.stringify(data);
        }
    });
    $.extend($.jgrid.del, {
        mtype: 'DELETE',
        serializeDelData: function() {
            return "";
        }
    });

    var editOptions = {
        onclickSubmit: function(params, postdata) {
            params.url = URL + '/' + postdata.id;
        }
    };
    var addOptions = {mtype: "POST"};

    var delOptions = {
        onclickSubmit: function(params, postdata) {
            params.url = URL + '/' + postdata;
        }
    };

    var URL = '/api/post';

    var options = {
        url: URL,
        editurl: URL,
        colModel:[
            {
                label: 'ID',
                name: 'id',
                key: true,
                formatter:'integer',
                editable: true,
                index: 'id',
                editoptions: {disabled: true, size:5},
                width: 50
            },
            {
                label: 'Title',
                name: 'title',
                index: 'title',
                editable: true,
                editrules: {required: true},
                width: 300
            },
            {
                label: 'Content',
                name: 'content',
                index: 'content',
                editable: true,
                editrules: {required: true},
                width: 600
            }
        ],
        caption: "Post",
        rowNum: 10,
        hidegrid: false,
        guiStyle: "bootstrap4",
        iconSet: "fontAwesome",
        loadonce: false,
        pager : '#pager',
        emptyrecords: "Nothing to display",
        sortname: 'id',
        sortorder: 'asc',
        viewrecords: true,
        gridview: true,
        height: 'auto',
        ondblClickRow: function(id) {
            jQuery(this).jqGrid('editGridRow', id, editOptions);
        }
    };

    $("#list")
        .jqGrid(options)
        .navGrid('#pager',
            {}, //options
            editOptions,
            addOptions,
            delOptions,
            {} // search options
        );

});

