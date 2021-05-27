import MongoDb from "mongodb"
const { MongoClient } = MongoDb

export const client = new MongoClient(process.env.MONGO_URL, {
  useNewUrlParser: true,
  useUnifiedTopology: true
})

client.connect()

export const makeMongoDbUserRepository = (client) => {
  const obfuscateUser = (user) => {
    delete user.password
  }

  const save = async (user) => {
    const collection = await client.db(process.env.DB_NAME).collection("users")
    const result = await collection.insertOne(user)
    return result.insertedId
  }

  const getByEmail = async (email) => {
    const collection = await client.db(process.env.DB_NAME).collection("users")
    const user = await collection.findOne({ email })
    user && obfuscateUser(user)
    return user
  }

  return { save, getByEmail }
}

export const mongDbUserRepository = makeMongoDbUserRepository(client)
