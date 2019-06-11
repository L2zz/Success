const axios = require("axios");
const cheerio = require("cheerio");
var admin = require('firebase-admin');

admin.initializeApp({
  credential: admin.credential.cert({
    projectId: 'success-f9291',
    clientEmail: 'firebase-adminsdk-psy8q@success-f9291.iam.gserviceaccount.com',
    privateKey: '-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQD3lfLo75aKUfod\n51pkhASXF6ELxHXqrkbMTCG33RihedMm+zo6wDjkOo84uCXf8TaK6+8bPeFSK9Vp\nLCFSnrQWjGo7bFZKnPrI/kPjHGgmdnKGlFT+XpOwkbAlTN5uUAdCFeRzJUzK8NH/\nLa0RwZPZ2lRZw8tSAC5M1iyZ4OSkVz5uHaN6E95hn6CQJIuiNJhae08nZMved78r\nOVgrtIXNEK94RXdPDdc1w17VClXdcfpHLChbigvz5vJ42Ks+ZP6mm2xJmYOrV+YS\nWBzh+rUroX+qSBRhiHRhKpG6YMWYY2BzEIS7tAmeflTr5XIdXJ/rtuCA8Y4ClKh8\nbIi9b3MrAgMBAAECggEAB7LaRfHmXSTP4YmHQ3FWM2KOHwbe1W5GVfFRXowfmrha\nvIxvO5iJ7z/zFsnUMdn55J1XydBLRwizzv9znUPv/NGB8Q1dIKRehlh2b331ru/E\nlrighCN1Xk1rnYsgxeAtow3W8ggGTPVfEY+SuInDAiWBtVJ+DT81AtC7AduQbZvp\naXn5f1coogE8MGksiRHRoSeTS5YMrjGVec4uCE20qKgSww4/lA6a0RQibORaA6nF\n+eS0F9ICcHQQi+faB3krnFhSruzJoPn9LQccuim7qzZqy70LkP5VVCuEbiWKwmTA\nTKbUB0PjNfy6FXlqd09g+Cn1omBso2b9PIoEzSsKKQKBgQD9UD2zRmMh85AUjAqf\nk6KKF8POr5dwfxbsao3hJEb0yAfpgL9F02B70aHTb9xBH+oxceOYMDvu2Qr/2YaO\nUuHsmRz9kEi+J0MRJ8TSKd3z5cv0cYIGwMjhst4Zq2ybUJWSlaMqLxvS8xksHne4\ng3V2q6RXmV0DpSEOmU60V9jtAwKBgQD6Nigmt2hzJiirddFL8EM5034zpx+8rRCd\nhSDcmhzkvD3Xc7v4m0sxmU82lfdlW+6N3IBS5CxXGG6QwtJaN4FV5GmXxtogYDUQ\nJovgb+nRwNGHUcIRu/yRSYSb+5ayfy9GGOA6tCohxmISncoZR4mVYG9E3G1CTAxh\nNJKweV1kuQKBgQDOUwEueHJeQQhl8/rjJ1kIqXqvao4q7xk4z0Fo1CeHUTZAlsE0\nmorHz2PD2CTKVrhmNaFvYDowvVAcPI1Ad3nVQJFAZrkZSNlzq7TAiaFi+xU4sQt7\nfKMiUpJczifUCbsKfh88SMqc86zZJqfU/h463mspfp0oTuOyBGT6t2LVCQKBgAy3\nofTNz6ZCo0CLvXaRoKkYwcpYTTmmxjhrYs8zmhJaKv5/aGdrZTgBJdCNcMSbFpXp\nRjBwXnySxlPfQxfIyG8YK/HF7qJEoY3Pl75xkb3SghPRqP6DwoOT7ASNlgw+gKWc\ncBErxRRwY920XWhJhYmtHBqw5XDAMsTiCi6kDIYpAoGAXn3AAj7HC05WxO16pjw9\nA23jOsUwafDK+7L2LqResp3B2vXiefkjRIKGt1qw2FOWrP5/yZdlV4PMJThuXMg2\n4jSE/krnrbSRXKJXXHX1HY8kG6i1GtYnV2nFfErLb7F/p9y3uYSt+Bkw8DT81Lu5\nTF2KjJZpHW5q+6/5KJlYGzA=\n-----END PRIVATE KEY-----\n'
  }),
  databaseURL: 'https://success-f9291.firebaseio.com'
});

const site = "http://icc.skku.ac.kr/icc_new/"
const compSite =  site + "board_list_square?listPage=1&boardName=board_news&field=subject&keyword="
const getHtml = async() => {
  try {
    return await axios.get(compSite);
  } catch(error) {
    console.log(error);
  }
};

getHtml().then(html => {
  let ulList = [];
  const $ = cheerio.load(html.data);
  const $bodyList = $("table.list-table tbody").children("tr");
  var db = admin.database();

  $bodyList.each(function(i, elm) {
    var id_, title_, url_, date_;
    if (i > 0) {
    const item = $(this).children("td").each(function (j, elm) {
      switch (j) {
        case 0:
          id_ = Number($(this).text().replace(/(\s*)/g, ""));
          break;
        case 1:
          title_ = $(this).text().replace(/(\s*)/g, "");
          url_ = site + $(this).children('a').attr('href');
          break;
        case 3:
          date_ = $(this).text().replace(/(\s*)/g, "");
          break;
        default:
          break;
      }
    });
    ulList[i-1] = {
      id: id_,
      title: title_,
      url: url_,
      date: date_
    };
  }
  });
  
  var lastRef = db.ref("site/1/category/1/last");
  var lastId;
  lastRef.once('value').then(function(snapshot) {
    lastId = Number(snapshot.val());
    return lastId;
  }).then(function(lastId) {
      if(lastId >= ulList[0].id) process.exit();
      console.log(lastId + " " + ulList[0].id);
      var articleRef = db.ref("site/1/category/1/article").push();
      lastRef.set(ulList[0].id);
      articleRef.set({
        id: ulList[0].id,
        title: ulList[0].title,
        url: ulList[0].url,
        date: ulList[0].date
      }, function(error) {
        if (error) {
          console.log("Data could not be saved.");
        } else {
          console.log("Data saved successfully.");
          process.exit();
        }
      })
    });
});
