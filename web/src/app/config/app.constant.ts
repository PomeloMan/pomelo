import { environment } from '../../environments/environment';

export const PREFIX = environment.prefix;
export const HOST = environment.host;
export const PORT = environment.port;
export const BASE_PATH = PREFIX + ':' + '//' + HOST + ':' + PORT + '/';

export const STORAGE_SETTING = '_settings';
export const STORAGE_SETTING_THEME = '_theme';
export const STORAGE_SETTING_STYLE = '_style';

export const useMockData = environment.useMockData;