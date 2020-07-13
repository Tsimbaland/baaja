import { SlideType } from 'app/shared/model/enumerations/slide-type.model';

export interface ISlide {
  id?: number;
  order?: number;
  name?: string;
  type?: SlideType;
  bodyId?: number;
  presentationId?: number;
}

export const defaultValue: Readonly<ISlide> = {};
