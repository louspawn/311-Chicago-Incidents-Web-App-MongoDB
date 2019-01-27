6. db.requests.aggregate([
{
    $project: {
        "typeOfServiceRequest": 1,
        "geoLocation": 1,
        "formatedDate": {
            $dateToString: {
                "format": "%Y-%m-%d",
                "date": "$creationDate"
            }
        }
    }
},
{
    $match: {
        "formatedDate": "2018-10-01",
        "geoLocation": {
            $geoWithin: {
                $box: [
                    [-113.0, 31.0],
                    [-77.0, 48.0]
                ]
            }
        }
    }
},
{
    $group: {
        "_id": "$typeOfServiceRequest",
        "total": {
            $sum: 1
        }
    }
},
{
    $project: {
        "typeOfServiceRequest": "$_id",
        "total": 1,
        "_id": 0
    }
},
{ $sort: { "total": -1 } },
{ $limit: 1 }
]);

8.  db.users.aggregate([ 
    { 
        $sort : { 
            "upvotesCount" : -1
        }
    }, 
    { 
        $limit : 50
    }, 
    { 
        $project : {
            "fullName" : 1 , "address" : 1 , "telephoneNumber" : 1 , "upvotesCount" : 1, "_id": 0
        }            
}]


10. db.requests.aggregate([
    { 
        $match : { "upvotesCount" : { $gt : 1}}
    },
    { 
        $unwind : "$upvotes"
    }, 
    { 
        $group : { 
            _id : { _id : "$_id" , telephoneNumber : "$upvotes.telephoneNumber"}, 
            total : { $sum : 1}
        }
    }, 
    {
        $match : { 
            "total" : { $gte : 2}
        }
    }, 
    { 
        $group : { 
            id : "$_id._id"
        }
    }
], { allowDiskUse: true });