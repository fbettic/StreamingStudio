export interface IStreamingPlatformRequest {
  platformName: string;
  email: string;
  apiUrl: string;
  imageUrl: string;
  authToken: string;
  signupFeeId: number;
  loginFeeId: number;
  serviceType: string;
}