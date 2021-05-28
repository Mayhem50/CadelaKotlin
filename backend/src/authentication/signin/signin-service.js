import { InternalError, InvalidParamError } from "../../shared/errors"

export const makeSigninService = ({
  emailValidator,
  userRepository,
  encrypter,
  tokenGenerator
} = {}) => {
  const sign = async (credential) => {
    try {
      if (!credential) {
        throw InvalidParamError("credential")
      }
      const { email, password } = credential
      if (!email) {
        throw InvalidParamError("email")
      }
      if (!password) {
        throw InvalidParamError("password")
      }

      if (!emailValidator.valid(email)) {
        throw InvalidParamError("email")
      }

      const foundUser = await userRepository.getByEmail(email)

      if (!foundUser) {
        throw InternalError("user not found")
      }

      const isRightPassword = await encrypter.compare(
        password,
        foundUser.password
      )

      if (!isRightPassword) {
        throw InternalError("wrong email/password")
      }

      const token = tokenGenerator.generate(foundUser.id)

      return {
        body: { token, user: { ...foundUser } }
      }
    } catch (error) {
      if (error.stack?.includes("TypeError")) {
        throw InternalError()
      }
      throw error ?? InternalError()
    }
  }
  return { sign }
}
