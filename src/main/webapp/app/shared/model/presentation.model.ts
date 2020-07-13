import { Moment } from 'moment';
import { ISlide } from 'app/shared/model/slide.model';

export interface IPresentation {
  id?: number;
  title?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  description?: string;
  slides?: ISlide[];
  authorId?: number;
}

export const defaultValue: Readonly<IPresentation> = {};
