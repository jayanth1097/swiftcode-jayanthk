var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('blue');
});

app.controller('chatController', function ($scope) {

    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
},
        {
            'sender': 'BOT',
            'text': 'Hi wat can i do for u'
},
        {
            'sender': 'USER',
            'text': 'just checking u out'
},
        {
            'sender': 'BOT',
            'text': 'sorry please i cant understand'
},
        {
            'sender': 'USER',
            'text': 'ok wats up'
},
        {
            'sender': 'BOT',
            'text': 'ask anything u want'
}
];
});