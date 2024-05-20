export interface IStreamingPlatform {
  platformId: number;
  platformName: string;
  email: string;
  apiUrl: string;
  imageUrl: string;
  authToken: string;
  signupFeeId: number;
  signupFeeType: string;
  signupFee: string;
  loginFeeId: number;
  loginFeeType: string;
  loginFee: string;
  serviceType: string;
}
