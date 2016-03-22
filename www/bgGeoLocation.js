/*global cordova, module*/

module.exports = {
  start: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "start", [data]);
  },
  stop_update: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "stop_update", [{}]);
  }
};
