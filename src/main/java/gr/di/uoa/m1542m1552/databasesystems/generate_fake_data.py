import pymongo
from faker import Faker
import numpy as np

def fake_telephone_number():
    telephone_number = '312-5'
    return telephone_number + str(np.random.randint(10, 99)) + '-' + str(np.random.randint(1000, 9999))

def incr(loop_idx):
    return 0 if loop_idx >= 69999 else loop_idx + 1

def add_upvote(usr_id, req_id, telephone_number, ward):
    usr_query = {"_id": usr_id}
    usr_values = {"$push": {"upvotes": {"requestId": str(req_id), "ward": ward}}, "$inc": {"upvotesCount": 1}}

    req_query = {"_id": req_id}
    req_values = {"$push": {"upvotes": {"userId": str(usr_id), "telephoneNumber": telephone_number}}, "$inc": {"upvotesCount": 1}}

    col_usr.update_one(usr_query, usr_values)
    col_req.update_one(req_query, req_values)


fake = Faker()

client = pymongo.MongoClient("mongodb://localhost:27017/")
db = client["chicago_incidents"]
col_req = db["requests"]
col_usr = db["users"]

create_users = True
create_requests = False

if create_users:
    user_list = []

    for user in range(70000):
        user_list.append({'myid': user,
                          'fullName': fake.name(), 
                          'address': fake.address(),
                          'telephoneNumber': fake_telephone_number(),
                          'upvotes': [],
                          'upvotesCount': 0})

    col_usr.insert_many(user_list)
    exit(0)

if create_requests:
    loop_idx = 0
    s = np.random.normal(0, 40, 70000) 
    user_available_votes = np.abs(s.round().astype(int)) 

    agg_pipeline = [{"$sample": {"size": 1000000}}, 
                    {"$project": {"_id": 1, "typeOfServiceRequest": 1, "ward": 1}}]
    random_requests = col_req.aggregate(agg_pipeline, allowDiskUse=True)

    for counter, request in enumerate(random_requests):
        if (counter % 50000) == 0:
            print(str(counter) + '/1000000')

        if 'ward' not in request: 
            continue

        num_upvotes = np.random.zipf(2.5)

        if num_upvotes > 3000:
            num_upvotes = 1

        for i in range(num_upvotes):
            while user_available_votes[loop_idx] == 0:
                loop_idx = incr(loop_idx)

            user_available_votes[loop_idx] -= 1
            usr_data = col_usr.find_one({"myid": loop_idx}, {"_id": 1, "telephoneNumber": 1})
            add_upvote(usr_data['_id'], request['_id'], usr_data['telephoneNumber'], request['ward'])
            loop_idx = incr(loop_idx)

            if loop_idx == 0:
                break

    exit(0)
