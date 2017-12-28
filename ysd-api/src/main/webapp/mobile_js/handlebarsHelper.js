 //格式化毫秒数(mod为ymd时输出年月日，为空输出到秒，其余输出到分)
Handlebars.registerHelper('prettifyDate', function (timestamp) {
    var d = new Date(timestamp);  
    var date = (d.getFullYear()) + "-" + 
       (d.getMonth() + 1) + "-" +
       (d.getDate()) + " " + 
       (d.getHours()) + ":" + 
       (d.getMinutes()) + ":" + 
       (d.getSeconds());
    return date;
});
Handlebars.registerHelper('formatDate', function (times) {
    var d = new Date(times);  
    var date = (d.getFullYear()) + "-" + 
       (d.getMonth() + 1) + "-" +
       (d.getDate()) ;
    return date;
});