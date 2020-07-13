export interface IBulletsIsBody {
  id?: number;
  isMultiSelect?: boolean;
}

export const defaultValue: Readonly<IBulletsIsBody> = {
  isMultiSelect: false
};
