import { jest, beforeEach } from "@jest/globals"
import { InternalError, InvalidParamError } from "@utils/errors"
import { makeGrantService } from "./grant-service"

const TOKEN = "any_token"
const USER_ID = "any_user_id"

const makeTokenDecoder = (isValid = true) => {
  const descrypt = jest.fn(async (token) => {
    if (!isValid) {
      throw InternalError()
    }
    return USER_ID
  })

  return { descrypt }
}

const tokenDecoder = makeTokenDecoder()

describe("Grant user", () => {
  it("Return user id if token is valid", async () => {
    const grantService = makeGrantService(tokenDecoder)
    const response = await grantService.grant(TOKEN)
    expect(tokenDecoder.descrypt).toBeCalledWith(TOKEN)
    expect(response.userId).toEqual(USER_ID)
  })

  it("Throw internal error if fail to decode", async () => {
    const tokenDecoder = makeTokenDecoder(false)
    const grantService = makeGrantService(tokenDecoder)
    await expect(grantService.grant(TOKEN)).rejects.toEqual(InternalError())
  })

  it("Throw internal error if no tokenDecoder injected", async () => {
    const grantService = makeGrantService()
    await expect(grantService.grant(TOKEN)).rejects.toEqual(InternalError())
  })

  it("Throw invalid parameter error if fail no token", async () => {
    const grantService = makeGrantService(tokenDecoder)
    await expect(grantService.grant()).rejects.toEqual(
      InvalidParamError("token")
    )
  })

  it("Throw invalid parameter error if fail token is empty string", async () => {
    const grantService = makeGrantService(tokenDecoder)
    await expect(grantService.grant("")).rejects.toEqual(
      InvalidParamError("token")
    )
  })
})
